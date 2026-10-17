package cl.duoc.tiotec.data.local

import androidx.room.*

@Entity(tableName = "perfil")
data class Perfil(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreFicticio: String,
    val nivelExperiencia: String, // basico | intermedio | avanzado
    val fechaCreacion: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "dispositivo",
    foreignKeys = [ForeignKey(entity = Perfil::class, parentColumns = ["id"],
        childColumns = ["perfilId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("perfilId")]
)
data class Dispositivo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val perfilId: Int,
    val nombre: String,
    val tipo: String,
    val sistemaOperativo: String,
    val evidenciaUri: String? = null
)

@Entity(tableName = "categoria")
data class Categoria(
    @PrimaryKey val id: Int,
    val nombre: String,
    val descripcion: String,
    val activa: Boolean = true
)

@Entity(
    tableName = "pregunta",
    foreignKeys = [ForeignKey(entity = Categoria::class, parentColumns = ["id"],
        childColumns = ["categoriaId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("categoriaId")]
)
data class Pregunta(
    @PrimaryKey val id: Int,
    val categoriaId: Int,
    val texto: String,
    val peso: Int = 1,
    val activa: Boolean = true,
    val estadoRevision: String = "aprobado"
)

@Entity(
    tableName = "recomendacion",
    foreignKeys = [ForeignKey(entity = Categoria::class, parentColumns = ["id"],
        childColumns = ["categoriaId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("categoriaId")]
)
data class Recomendacion(
    @PrimaryKey val id: Int,
    val categoriaId: Int,
    val titulo: String,
    val descripcion: String,
    val nivelRiesgoAsociado: String, // medio | alto
    val estadoRevision: String = "aprobado"
)

@Entity(
    tableName = "evaluacion",
    foreignKeys = [ForeignKey(entity = Perfil::class, parentColumns = ["id"],
        childColumns = ["perfilId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("perfilId")]
)
data class Evaluacion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val perfilId: Int,
    val dispositivoId: Int? = null,
    val fecha: Long = System.currentTimeMillis(),
    val puntajeTotal: Int,
    val nivelRiesgoGlobal: String
)

@Entity(
    tableName = "respuesta",
    foreignKeys = [ForeignKey(entity = Evaluacion::class, parentColumns = ["id"],
        childColumns = ["evaluacionId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("evaluacionId")]
)
data class Respuesta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val evaluacionId: Int,
    val preguntaId: Int,
    val valor: Int // 0 = no, 1 = a veces / no sé, 2 = sí
)

@Entity(
    tableName = "resultado_categoria",
    foreignKeys = [ForeignKey(entity = Evaluacion::class, parentColumns = ["id"],
        childColumns = ["evaluacionId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("evaluacionId")]
)
data class ResultadoCategoria(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val evaluacionId: Int,
    val categoriaId: Int,
    val puntaje: Int, // porcentaje 0..100
    val nivelRiesgo: String // bajo | medio | alto
)

@Entity(
    tableName = "accion_checklist",
    foreignKeys = [ForeignKey(entity = Evaluacion::class, parentColumns = ["id"],
        childColumns = ["evaluacionId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("evaluacionId")]
)
data class AccionChecklist(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val evaluacionId: Int,
    val recomendacionId: Int,
    val prioridad: Int, // 1 = más urgente
    val estado: String = "pendiente", // pendiente | completada
    val fechaRevision: Long? = null,
    val fechaCompletada: Long? = null
)

@Entity(tableName = "solicitud_asistencia")
data class SolicitudAsistencia(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val perfilId: Int,
    val categoriaId: Int,
    val descripcion: String,
    val estado: String = "abierta", // abierta | en_proceso | cerrada
    val fecha: Long = System.currentTimeMillis()
)
