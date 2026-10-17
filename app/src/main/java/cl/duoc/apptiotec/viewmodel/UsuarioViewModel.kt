package cl.duoc.apptiotec.viewmodel

import androidx.lifecycle.ViewModel
import cl.duoc.apptiotec.model.UsuarioErrores
import cl.duoc.apptiotec.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    // Funciones para actualizar cada campo del formulario
    fun onNombreChanged(nuevoNombre: String) {
        _uiState.update { actual ->
            actual.copy(
                nombre = nuevoNombre,
                errores = actual.errores.copy(nombre = null)
            )
        }
    }

    fun onCorreoChanged(nuevoCorreo: String) {
        _uiState.update { actual ->
            actual.copy(
                correo = nuevoCorreo,
                errores = actual.errores.copy(correo = null)
            )
        }
    }

    fun onClaveChanged(nuevaClave: String) {
        _uiState.update { actual ->
            actual.copy(
                clave = nuevaClave,
                errores = actual.errores.copy(clave = null)
            )
        }
    }

    fun onDireccionChanged(nuevaDireccion: String) {
        _uiState.update { actual ->
            actual.copy(
                direccion = nuevaDireccion,
                errores = actual.errores.copy(direccion = null)
            )
        }
    }

    fun onAceptaTerminosChanged(acepta: Boolean) {
        _uiState.update { actual ->
            actual.copy(aceptaTerminos = acepta)
        }
    }

    // Lógica de validación del formulario
    fun validarFormulario(): Boolean {
        val estadoActual = _uiState.value

        val errorNombre = if (estadoActual.nombre.isBlank()) "El nombre no puede estar vacío" else null
        val errorCorreo = when {
            estadoActual.correo.isBlank() -> "El correo es obligatorio"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(estadoActual.correo).matches() -> "Formato de correo inválido"
            else -> null
        }
        val errorClave = when {
            estadoActual.clave.isBlank() -> "La contraseña no puede estar vacía"
            estadoActual.clave.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }
        val errorDireccion = if (estadoActual.direccion.isBlank()) "La dirección es obligatoria" else null

        val nuevosErrores = UsuarioErrores(
            nombre = errorNombre,
            correo = errorCorreo,
            clave = errorClave,
            direccion = errorDireccion
        )

        _uiState.update { it.copy(errores = nuevosErrores) }

        // Retorna true solo si todos los errores son nulos
        return errorNombre == null && errorCorreo == null && errorClave == null && errorDireccion == null
    }
}