package cl.duoc.apptiotec.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.apptiotec.viewmodel.UsuarioViewModel

@Composable
fun FormularioScreen(
    viewModel: UsuarioViewModel,
    onNavegarResultado: () -> Unit = {}
) {
    // Escuchar la persistencia en tiempo real desde DataStore
    val nombreGuardado by viewModel.nombreGuardado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Formulario de Registro (Guía 12)",
            style = MaterialTheme.typography.headlineSmall
        )

        // Campo Nombre
        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Campo Correo
        OutlinedTextField(
            value = viewModel.correo,
            onValueChange = { viewModel.correo = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Campo Dirección
        OutlinedTextField(
            value = viewModel.direccion,
            onValueChange = { viewModel.direccion = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // PARTE 3: Animación reactiva basada en derivedStateOf
        AnimatedVisibility(
            visible = viewModel.esFormularioValido,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Text(
                text = "✓ Formulario válido. Puedes guardar la información.",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de guardado
        Button(
            onClick = {
                viewModel.guardarUsuario()
                onNavegarResultado()
            },
            enabled = viewModel.esFormularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Usuario")
        }

        // PARTE 2: Animación para mostrar el dato guardado en local (DataStore)
        AnimatedVisibility(
            visible = nombreGuardado.isNotBlank(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = "Usuario guardado en DataStore: $nombreGuardado",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}