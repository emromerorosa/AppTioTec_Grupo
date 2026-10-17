package cl.duoc.tiotec.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(nombre: String, alNuevaEvaluacion: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Hola, $nombre", style = MaterialTheme.typography.headlineMedium)
        Text("Revisa tus hábitos de seguridad digital antes de que ocurra un problema.")
        // TODO: tarjeta con último resultado, acciones pendientes y próxima revisión.
        Button(onClick = alNuevaEvaluacion, modifier = Modifier.fillMaxWidth()) {
            Text("Nueva evaluación")
        }
    }
}
