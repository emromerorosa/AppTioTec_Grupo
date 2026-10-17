package cl.duoc.tiotec.data.repository

import cl.duoc.tiotec.data.local.*
import cl.duoc.tiotec.data.remote.TioTecApi
import cl.duoc.tiotec.domain.Riesgo
import cl.duoc.tiotec.domain.RiesgoCalculator
import kotlinx.coroutines.flow.Flow

/**
 * Las pantallas leen SIEMPRE desde Room; la API solo alimenta a Room.
 * Así la app funciona sin conexión.
 */
class TioTecRepository(private val db: AppDatabase, private val api: TioTecApi) {

    val perfil: Flow<Perfil?> = db.perfilDao().observarPerfil()
    val categorias: Flow<List<Categoria>> = db.cuestionarioDao().observarCategorias()
    val preguntas: Flow<List<Pregunta>> = db.cuestionarioDao().observarPreguntas()
    val checklist: Flow<List<AccionChecklist>> = db.evaluacionDao().checklist()

    suspend fun crearPerfil(nombre: String, nivel: String) =
        db.perfilDao().insertar(Perfil(nombreFicticio = nombre, nivelExperiencia = nivel))

    fun historial(perfilId: Int) = db.evaluacionDao().historial(perfilId)

    /** Descarga el cuestionario desde la API y lo guarda en Room. Falla sin romper la app. */
    suspend fun sincronizarCuestionario(): Result<Unit> = runCatching {
        val dao = db.cuestionarioDao()
        dao.guardarCategorias(api.categorias())
        dao.guardarPreguntas(api.preguntas())
        dao.guardarRecomendaciones(api.recomendaciones())
    }

    /** Guarda la evaluación, calcula el riesgo por categoría y arma el checklist priorizado. */
    suspend fun guardarEvaluacion(perfilId: Int, respuestas: Map<Int, Int>): Int {
        val preguntas = db.cuestionarioDao().preguntas()
        val recomendaciones = db.cuestionarioDao().recomendaciones()

        // 1) puntaje por categoría
        val porCategoria = preguntas.groupBy { it.categoriaId }.mapValues { (_, ps) ->
            RiesgoCalculator.porcentaje(ps.mapNotNull { respuestas[it.id] })
        }
        val global = if (porCategoria.isEmpty()) 0 else porCategoria.values.sum() / porCategoria.size

        // 2) guardar evaluación, respuestas y resultados
        val evalId = db.evaluacionDao().insertarEvaluacion(
            Evaluacion(perfilId = perfilId, puntajeTotal = global,
                nivelRiesgoGlobal = RiesgoCalculator.nivel(global).clave)
        ).toInt()
        db.evaluacionDao().insertarRespuestas(respuestas.map { (pid, v) -> Respuesta(evaluacionId = evalId, preguntaId = pid, valor = v) })
        db.evaluacionDao().insertarResultados(porCategoria.map { (cid, pct) ->
            ResultadoCategoria(evaluacionId = evalId, categoriaId = cid, puntaje = pct,
                nivelRiesgo = RiesgoCalculator.nivel(pct).clave)
        })

        // 3) checklist: recomendaciones de categorías con riesgo medio/alto, las más débiles primero
        val acciones = porCategoria.entries
            .filter { RiesgoCalculator.nivel(it.value) != Riesgo.BAJO }
            .sortedBy { it.value }
            .flatMapIndexed { i, (cid, _) ->
                recomendaciones.filter { it.categoriaId == cid }
                    .map { AccionChecklist(evaluacionId = evalId, recomendacionId = it.id, prioridad = i + 1) }
            }
        db.evaluacionDao().insertarAcciones(acciones)
        return evalId
    }

    suspend fun marcarAccion(id: Int, completada: Boolean) =
        db.evaluacionDao().cambiarEstado(id, if (completada) "completada" else "pendiente",
            if (completada) System.currentTimeMillis() else null)
}
