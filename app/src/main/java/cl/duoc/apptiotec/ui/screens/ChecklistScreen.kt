package cl.duoc.apptiotec.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.apptiotec.viewmodel.ChecklistViewModel

@Composable
fun ChecklistScreen(viewModel: ChecklistViewModel = viewModel()) {
    val tareas by viewModel.tareas.collectAsState()
    val progreso by viewModel.progreso.collectAsState()
    var textoNuevaTarea by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 1. Barra de progreso (Seguimiento)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Nivel de Protección: ${(progreso * 100).toInt()}% 🛡️",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.fillMaxWidth().height(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Formulario POST (Crear tarea)
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = textoNuevaTarea,
                onValueChange = { textoNuevaTarea = it },
                label = { Text("Nueva medida preventiva") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                viewModel.agregarTarea(textoNuevaTarea)
                textoNuevaTarea = ""
            }) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Lista CRUD (GET / PUT / DELETE)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tareas) { tarea ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = tarea.completed,
                            onCheckedChange = { viewModel.cambiarEstadoTarea(tarea) }
                        )
                        Text(
                            text = tarea.title,
                            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                        )
                        IconButton(onClick = { viewModel.eliminarTarea(tarea.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}