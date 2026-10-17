package cl.duoc.apptiotec.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultadoScreen(
    nombre: String,
    correo: String,
    direccion: String,
    onVolverClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen de Registro") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "¡Registro Exitoso!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Nombre:", style = MaterialTheme.typography.labelLarge)
                    Text(text = nombre, style = MaterialTheme.typography.bodyLarge)

                    Divider(modifier = Modifier.padding(vertical = 4.dp))

                    Text(text = "Correo:", style = MaterialTheme.typography.labelLarge)
                    Text(text = correo, style = MaterialTheme.typography.bodyLarge)

                    Divider(modifier = Modifier.padding(vertical = 4.dp))

                    Text(text = "Dirección:", style = MaterialTheme.typography.labelLarge)
                    Text(text = direccion, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onVolverClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Inicio")
            }
        }
    }
}