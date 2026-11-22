package cl.duoc.apptiotec.data.repository

import cl.duoc.apptiotec.data.remote.ApiService
import cl.duoc.apptiotec.data.remote.RetrofitInstance
import cl.duoc.apptiotec.model.TareaPreventiva

// La API llega como parámetro para poder reemplazarla por una falsa en las pruebas.
// Por defecto usa la real, así la app funciona igual que antes.
class TareaRepository(
    private val api: ApiService = RetrofitInstance.api
) {
    suspend fun obtenerTareas(): List<TareaPreventiva> = api.getTareas()
    suspend fun crearTarea(tarea: TareaPreventiva) = api.crearTarea(tarea)
    suspend fun actualizarTarea(tarea: TareaPreventiva) = api.actualizarTarea(tarea.id, tarea)
    suspend fun eliminarTarea(id: Int) = api.eliminarTarea(id)
}