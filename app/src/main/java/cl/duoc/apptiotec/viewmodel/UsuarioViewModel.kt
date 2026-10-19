package cl.duoc.apptiotec.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.apptiotec.data.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsuarioViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    // Campos del formulario
    var nombre by mutableStateOf("")
    var correo by mutableStateOf("")
    var direccion by mutableStateOf("")

    // Estado derivado para validar el formulario eficientemente (Parte 1 - Guía 12)
    val esFormularioValido by derivedStateOf {
        nombre.isNotBlank() && correo.contains("@") && direccion.isNotBlank()
    }

    // Observar el nombre guardado en DataStore (Parte 2 - Guía 12)
    val nombreGuardado: StateFlow<String> = userPreferences.userName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    // Guardar el nombre en DataStore local
    fun guardarUsuario() {
        if (esFormularioValido) {
            viewModelScope.launch {
                userPreferences.saveUserName(nombre)
            }
        }
    }

    // Limpiar los campos del formulario
    fun limpiarFormulario() {
        nombre = ""
        correo = ""
        direccion = ""
    }
}