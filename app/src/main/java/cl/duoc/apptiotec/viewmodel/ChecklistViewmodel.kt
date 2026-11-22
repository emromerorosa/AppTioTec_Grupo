package cl.duoc.apptiotec.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.apptiotec.data.repository.TareaRepository
import cl.duoc.apptiotec.model.TareaPreventiva
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// El repositorio llega como parámetro para poder reemplazarlo por uno falso en las pruebas.
// Por defecto usa el real, así la app funciona igual que antes.
class ChecklistViewModel(
    private val repository: TareaRepository = TareaRepository()
) : ViewModel() {

    private val _tareas = MutableStateFlow<List<TareaPreventiva>>(emptyList())
    val tareas: StateFlow<List<TareaPreventiva>> = _tareas

    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso

    private val consejosEnEspanol = listOf(
        "Nunca entregar claves ni códigos que lleguen por mensaje de texto",
        "Activar la clave adicional de seguridad en WhatsApp",
        "Llamar directamente al banco antes de hacer una transferencia urgente",
        "No abrir enlaces extraños que lleguen por mensaje o correo",
        "Usar una clave distinta y difícil de adivinar para el banco",
        "Bloquear la pantalla del celular con huella, rostro o clave"
    )

    init {
        cargarTareas()
    }

    fun cargarTareas() {
        viewModelScope.launch {
            try {
                val listaApi = repository.obtenerTareas()
                val listaTraducida = listaApi.mapIndexed { index, tarea ->
                    val textoEspanol = consejosEnEspanol.getOrElse(index) {
                        "Revisar que solo tengas instaladas aplicaciones conocidas"
                    }
                    tarea.copy(title = textoEspanol)
                }
                _tareas.value = listaTraducida
                calcularProgreso(listaTraducida)
            } catch (e: Exception) {
                // Manejo de error de conexión
            }
        }
    }

    fun agregarTarea(titulo: String) {
        if (titulo.isBlank()) return
        viewModelScope.launch {
            try {
                val nueva = TareaPreventiva(
                    // Toma el id más alto de la lista y le suma 1 (si la lista está vacía, parte en 1)
                    id = (_tareas.value.maxOfOrNull { it.id } ?: 0) + 1,
                    title = titulo,
                    completed = false
                )
                repository.crearTarea(nueva)
                _tareas.value = _tareas.value + nueva
                calcularProgreso(_tareas.value)
            } catch (e: Exception) { }
        }
    }

    fun cambiarEstadoTarea(tarea: TareaPreventiva) {
        viewModelScope.launch {
            try {
                val actualizada = tarea.copy(completed = !tarea.completed)
                repository.actualizarTarea(actualizada)
                _tareas.value = _tareas.value.map { if (it.id == tarea.id) actualizada else it }
                calcularProgreso(_tareas.value)
            } catch (e: Exception) { }
        }
    }

    fun eliminarTarea(id: Int) {
        viewModelScope.launch {
            try {
                repository.eliminarTarea(id)
                _tareas.value = _tareas.value.filter { it.id != id }
                calcularProgreso(_tareas.value)
            } catch (e: Exception) { }
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