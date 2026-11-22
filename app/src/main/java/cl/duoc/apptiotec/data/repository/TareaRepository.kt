package cl.duoc.apptiotec.data.repository

import cl.duoc.apptiotec.data.remote.RetrofitInstance
import cl.duoc.apptiotec.data.remote.TareaApiService
import cl.duoc.apptiotec.model.TareaPreventiva

// La API llega como parámetro para poder reemplazarla por una falsa en las pruebas.
// Por defecto usa la conexión al microservicio propio (Spring Boot).
class TareaRepository(
    private val api: TareaApiService = RetrofitInstance.tareaApi
) {
    suspend fun obtenerTareas(): List<TareaPreventiva> = api.getTareas()
    suspend fun crearTarea(tarea: TareaPreventiva) = api.crearTarea(tarea)
    suspend fun actualizarTarea(tarea: TareaPreventiva) = api.actualizarTarea(tarea.id, tarea)
    suspend fun eliminarTarea(id: Int) = api.eliminarTarea(id)
}