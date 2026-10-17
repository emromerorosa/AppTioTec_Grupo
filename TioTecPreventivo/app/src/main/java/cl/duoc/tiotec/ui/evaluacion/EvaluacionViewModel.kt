package cl.duoc.tiotec.ui.evaluacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cl.duoc.tiotec.data.local.Pregunta
import cl.duoc.tiotec.data.repository.TioTecRepository
import cl.duoc.tiotec.ui.common.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EvaluacionViewModel(private val repo: TioTecRepository) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<List<Pregunta>>>(UiState.Cargando)
    val estado: StateFlow<UiState<List<Pregunta>>> = _estado.asStateFlow()

    private val _respuestas = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val respuestas: StateFlow<Map<Int, Int>> = _respuestas.asStateFlow()

    init {
        viewModelScope.launch {
            // Intenta actualizar desde la API; si falla, igual se usa lo que haya en Room.
            repo.sincronizarCuestionario()
            repo.preguntas.collect { lista ->
                _estado.value =
                    if (lista.isEmpty()) UiState.Error("No hay preguntas. Revisa tu conexión e inténtalo de nuevo.")
                    else UiState.Exito(lista)
            }
        }
    }

    fun responder(preguntaId: Int, valor: Int) {
        _respuestas.update { it + (preguntaId to valor) }
    }

    /** Guarda la evaluación y devuelve su id para navegar a Resultados. */
    fun finalizar(perfilId: Int, alTerminar: (Int) -> Unit) {
        viewModelScope.launch {
            alTerminar(repo.guardarEvaluacion(perfilId, _respuestas.value))
        }
    }

    class Factory(private val repo: TioTecRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = EvaluacionViewModel(repo) as T
    }
}
