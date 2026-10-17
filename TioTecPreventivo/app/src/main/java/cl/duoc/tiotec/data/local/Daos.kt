package cl.duoc.tiotec.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PerfilDao {
    @Insert suspend fun insertar(perfil: Perfil): Long
    @Query("SELECT * FROM perfil LIMIT 1") fun observarPerfil(): Flow<Perfil?>
}

@Dao
interface DispositivoDao {
    @Query("SELECT * FROM dispositivo WHERE perfilId = :perfilId")
    fun observarPorPerfil(perfilId: Int): Flow<List<Dispositivo>>
    @Insert suspend fun insertar(d: Dispositivo)
    @Update suspend fun actualizar(d: Dispositivo)
    @Delete suspend fun eliminar(d: Dispositivo)
}

@Dao
interface CuestionarioDao {
    @Query("SELECT * FROM categoria WHERE activa = 1") fun observarCategorias(): Flow<List<Categoria>>
    @Query("SELECT * FROM pregunta WHERE activa = 1") suspend fun preguntas(): List<Pregunta>
    @Query("SELECT * FROM pregunta WHERE activa = 1") fun observarPreguntas(): Flow<List<Pregunta>>
    @Query("SELECT * FROM recomendacion") suspend fun recomendaciones(): List<Recomendacion>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun guardarCategorias(l: List<Categoria>)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun guardarPreguntas(l: List<Pregunta>)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun guardarRecomendaciones(l: List<Recomendacion>)
}

@Dao
interface EvaluacionDao {
    @Insert suspend fun insertarEvaluacion(e: Evaluacion): Long
    @Insert suspend fun insertarRespuestas(l: List<Respuesta>)
    @Insert suspend fun insertarResultados(l: List<ResultadoCategoria>)
    @Insert suspend fun insertarAcciones(l: List<AccionChecklist>)

    @Query("SELECT * FROM evaluacion WHERE perfilId = :perfilId ORDER BY fecha DESC")
    fun historial(perfilId: Int): Flow<List<Evaluacion>>

    @Query("SELECT * FROM resultado_categoria WHERE evaluacionId = :evaluacionId")
    fun resultados(evaluacionId: Int): Flow<List<ResultadoCategoria>>

    @Query("SELECT * FROM accion_checklist ORDER BY estado DESC, prioridad")
    fun checklist(): Flow<List<AccionChecklist>>

    @Query("UPDATE accion_checklist SET estado = :estado, fechaCompletada = :fecha WHERE id = :id")
    suspend fun cambiarEstado(id: Int, estado: String, fecha: Long?)
}

@Database(
    entities = [Perfil::class, Dispositivo::class, Categoria::class, Pregunta::class,
        Recomendacion::class, Evaluacion::class, Respuesta::class,
        ResultadoCategoria::class, AccionChecklist::class, SolicitudAsistencia::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun perfilDao(): PerfilDao
    abstract fun dispositivoDao(): DispositivoDao
    abstract fun cuestionarioDao(): CuestionarioDao
    abstract fun evaluacionDao(): EvaluacionDao
}
