package cl.duoc.apptiotec.data.repository

import cl.duoc.apptiotec.data.remote.RetrofitInstance
import cl.duoc.apptiotec.data.remote.TareaApiService
import cl.duoc.apptiotec.model.TareaPreventiva
import retrofit2.HttpException

// La API llega como parámetro para poder reemplazarla por una falsa en las pruebas.
// Por defecto usa la conexión al microservicio propio (Spring Boot).
class TareaRepository(
    private val api: TareaApiService = RetrofitInstance.tareaApi
) {
    suspend fun obtenerTareas(): List<TareaPreventiva> = api.getTareas()
    suspend fun crearTarea(tarea: TareaPreventiva) = api.crearTarea(tarea)
    suspend fun actualizarTarea(tarea: TareaPreventiva) = api.actualizarTarea(tarea.id, tarea)

    // Borra la tarea; si el servidor no responde con éxito, lanza el error
    suspend fun eliminarTarea(id: Int) {
        val respuesta = api.eliminarTarea(id)
        if (!respuesta.isSuccessful) throw HttpException(respuesta)
    }
}