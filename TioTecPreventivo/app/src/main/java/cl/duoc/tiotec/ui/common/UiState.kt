package cl.duoc.tiotec.ui.common

/** Estado que cada pantalla observa: cargando, éxito o error. */
sealed interface UiState<out T> {
    data object Cargando : UiState<Nothing>
    data class Exito<T>(val datos: T) : UiState<T>
    data class Error(val mensaje: String) : UiState<Nothing>
}
