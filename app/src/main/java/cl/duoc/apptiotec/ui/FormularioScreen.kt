package cl.duoc.apptiotec.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cl.duoc.apptiotec.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    viewModel: UsuarioViewModel,
    onNavigateToResult: (nombre: String, correo: String, direccion: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Usuario") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Campo Nombre
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChanged(it) },
                label = { Text("Nombre Completo") },
                isError = uiState.errores.nombre != null,
                supportingText = {
                    uiState.errores.nombre?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Correo
            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { viewModel.onCorreoChanged(it) },
                label = { Text("Correo Electrónico") },
                isError = uiState.errores.correo != null,
                supportingText = {
                    uiState.errores.correo?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Contraseña
            OutlinedTextField(
                value = uiState.clave,
                onValueChange = { viewModel.onClaveChanged(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.errores.clave != null,
                supportingText = {
                    uiState.errores.clave?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Dirección
            OutlinedTextField(
                value = uiState.direccion,
                onValueChange = { viewModel.onDireccionChanged(it) },
                label = { Text("Dirección") },
                isError = uiState.errores.direccion != null,
                supportingText = {
                    uiState.errores.direccion?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Checkbox Términos y Condiciones
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = uiState.aceptaTerminos,
                    onCheckedChange = { viewModel.onAceptaTerminosChanged(it) }
                )
                Text("Acepto los términos y condiciones")
            }

            // Botón de Envío
            Button(
                onClick = {
                    if (viewModel.validarFormulario()) {
                        onNavigateToResult(uiState.nombre, uiState.correo, uiState.direccion)
                    }
                },
                enabled = uiState.aceptaTerminos,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Registrar")
            }
        }
    }
}