package cl.duoc.apptiotec.data.repository

import cl.duoc.apptiotec.data.remote.RetrofitInstance
import cl.duoc.apptiotec.model.TareaPreventiva

class TareaRepository {
    private val api = RetrofitInstance.api

    suspend fun obtenerTareas(): List<TareaPreventiva> = api.getTareas()
    suspend fun crearTarea(tarea: TareaPreventiva) = api.crearTarea(tarea)
    suspend fun actualizarTarea(tarea: TareaPreventiva) = api.actualizarTarea(tarea.id, tarea)
    suspend fun eliminarTarea(id: Int) = api.eliminarTarea(id)
}