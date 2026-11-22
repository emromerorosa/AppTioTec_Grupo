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

    init {
        cargarTareas()
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
            }
        }
    }

    fun agregarTarea(titulo: String) {
        if (titulo.isBlank()) return
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
            }
        }
    }

    fun cambiarEstadoTarea(tarea: TareaPreventiva) {
        viewModelScope.launch {
            try {
                val actualizada = tarea.copy(completed = !tarea.completed)
                repository.actualizarTarea(actualizada)
                _tareas.value = _tareas.value.map { if (it.id == tarea.id) actualizada else it }
                calcularProgreso(_tareas.value)
            } catch (e: Exception) {
                Log.e("TAREAS", "Error al actualizar tarea: ${e.message}", e)
            }
        }
    }

    fun eliminarTarea(id: Int) {
        viewModelScope.launch {
            try {
                repository.eliminarTarea(id)
                _tareas.value = _tareas.value.filter { it.id != id }
                calcularProgreso(_tareas.value)
            } catch (e: Exception) {
                Log.e("TAREAS", "Error al eliminar tarea: ${e.message}", e)
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