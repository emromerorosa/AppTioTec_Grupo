package cl.duoc.apptiotec.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.duoc.apptiotec.R

// Pantalla para tablets grandes (ancho Expandido).
// Diferencias con las otras dos:
// - más espacio alrededor (48 dp)
// - textos más grandes
// - la imagen es más alta
// - hay DOS botones, uno al lado del otro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenExpandida() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "TíoTec") })
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(48.dp),                                   // margen más grande
            horizontalArrangement = Arrangement.spacedBy(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lado izquierdo: imagen grande.
            Image(
                painter = painterResource(id = R.drawable.logo_tiotec),
                contentDescription = "Logo de TíoTec",
                modifier = Modifier
                    .weight(1f)
                    .height(360.dp),
                contentScale = ContentScale.Fit
            )

            // Lado derecho: textos y botones.
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "¡Bienvenida a TíoTec!",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.displaySmall // título grande
                )

                Text(
                    text = "Revisa tus hábitos de seguridad digital antes de que ocurra un problema.",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Dos botones en una fila.
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = { /* acción futura */ }) {
                        Text(text = "Nueva evaluación")
                    }
                    // OutlinedButton es un botón con borde y sin relleno.
                    OutlinedButton(onClick = { /* acción futura */ }) {
                        Text(text = "Ver historial")
                    }
                }
            }
        }
    }
}

// Preview que simula una tablet grande de 1000 x 800 dp.
@Preview(name = "Expanded", widthDp = 1000, heightDp = 800)
@Composable
fun PreviewExpanded() {
    HomeScreenExpandida()
}