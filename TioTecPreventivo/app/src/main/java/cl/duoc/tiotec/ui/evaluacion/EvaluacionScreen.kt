package cl.duoc.tiotec.ui.evaluacion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.duoc.tiotec.ui.common.UiState

@Composable
fun EvaluacionScreen(vm: EvaluacionViewModel, perfilId: Int, alTerminar: (Int) -> Unit) {
    val estado by vm.estado.collectAsStateWithLifecycle()
    val respuestas by vm.respuestas.collectAsStateWithLifecycle()

    when (val e = estado) {
        UiState.Cargando -> Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
        is UiState.Error -> Text(e.mensaje, Modifier.padding(16.dp))
        is UiState.Exito -> Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text("Evaluación", style = MaterialTheme.typography.headlineSmall)
            Text("Aviso: no ingreses datos reales.", style = MaterialTheme.typography.bodySmall)
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(e.datos) { p ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(p.texto)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("No" to 0, "A veces" to 1, "Sí" to 2).forEach { (txt, v) ->
                                    FilterChip(
                                        selected = respuestas[p.id] == v,
                                        onClick = { vm.responder(p.id, v) },
                                        label = { Text(txt) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Button(
                onClick = { vm.finalizar(perfilId, alTerminar) },
                enabled = respuestas.size == e.datos.size,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Ver resultados") }
        }
    }
}
