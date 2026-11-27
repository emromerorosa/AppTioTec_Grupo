package cl.duoc.apptiotec.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.apptiotec.data.repository.TareaRepository
import cl.duoc.apptiotec.model.TareaPreventiva
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// El repositorio llega como parámetro para poder reemplazarlo por uno falso en las pruebas.
// Por defecto usa el real, que se conecta al microservicio propio (Spring Boot).
class ChecklistViewModel(
    private val repository: TareaRepository = TareaRepository()
) : ViewModel() {

    private val _tareas = MutableStateFlow<List<TareaPreventiva>>(emptyList())
    val tareas: StateFlow<List<TareaPreventiva>> = _tareas

    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso

    // Mensaje de error para mostrar en pantalla (null = no hay error)
    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError

    // Ids de las tareas que se están procesando, para ignorar toques repetidos
    private val tareasEnProceso = mutableSetOf<Int>()

    // Indica si ya se está agregando una tarea, para evitar duplicados
    private var agregando = false

    init {
        cargarTareas()
    }

    // Borra el mensaje de error de la pantalla
    fun limpiarError() {
        _mensajeError.value = null
    }

    fun cargarTareas() {
        viewModelScope.launch {
            try {
                // Pide las tareas al microservicio y las muestra tal cual vienen
                val lista = repository.obtenerTareas()
                _tareas.value = lista
                calcularProgreso(lista)
            } catch (e: Exception) {
                Log.e("TAREAS", "Error al cargar tareas: ${e.message}", e)
                _mensajeError.value = "No se pudieron cargar las tareas. Revisa la conexión con el servidor."
            }
        }
    }

    fun agregarTarea(titulo: String) {
        if (titulo.isBlank()) return
        // Si ya se está agregando una tarea, ignora el toque repetido
        if (agregando) return
        agregando = true
        viewModelScope.launch {
            try {
                // El id se manda en 0: el microservicio asigna el id real
                val nueva = TareaPreventiva(
                    id = 0,
                    title = titulo,
                    completed = false
                )
                // El microservicio devuelve la tarea creada, ya con su id verdadero
                val creada = repository.crearTarea(nueva)
                _tareas.value = _tareas.value + creada
                calcularProgreso(_tareas.value)
            } catch (e: Exception) {
                Log.e("TAREAS", "Error al crear tarea: ${e.message}", e)
                _mensajeError.value = "No se pudo agregar la tarea. Revisa la conexión con el servidor."
            } finally {
                agregando = false
            }
        }
    }

    fun cambiarEstadoTarea(tarea: TareaPreventiva) {
        // Si esta tarea ya se está actualizando, ignora el toque repetido
        if (!tareasEnProceso.add(tarea.id)) return
        viewModelScope.launch {
            try {
                val actualizada = tarea.copy(completed = !tarea.completed)
                repository.actualizarTarea(actualizada)
                _tareas.value = _tareas.value.map { if (it.id == tarea.id) actualizada else it }
                calcularProgreso(_tareas.value)
            } catch (e: Exception) {
                Log.e("TAREAS", "Error al actualizar tarea: ${e.message}", e)
                _mensajeError.value = "No se pudo actualizar la tarea. Revisa la conexión con el servidor."
            } finally {
                tareasEnProceso.remove(tarea.id)
            }
        }
    }

    fun eliminarTarea(id: Int) {
        // Si esta tarea ya se está borrando, ignora el toque repetido
        if (!tareasEnProceso.add(id)) return
        viewModelScope.launch {
            try {
                repository.eliminarTarea(id)
                _tareas.value = _tareas.value.filter { it.id != id }
                calcularProgreso(_tareas.value)
            } catch (e: Exception) {
                Log.e("TAREAS", "Error al eliminar tarea: ${e.message}", e)
                _mensajeError.value = "No se pudo eliminar la tarea. Revisa la conexión con el servidor."
            } finally {
                tareasEnProceso.remove(id)
            }
        }
    }

    private fun calcularProgreso(lista: List<TareaPreventiva>) {
        if (lista.isEmpty()) {
            _progreso.value = 0f
            return
        }
        val completadas = lista.count { it.completed }
        _progreso.value = completadas.toFloat() / lista.size.toFloat()
    }
}