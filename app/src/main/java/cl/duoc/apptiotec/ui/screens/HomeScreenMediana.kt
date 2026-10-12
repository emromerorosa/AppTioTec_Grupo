package cl.duoc.apptiotec.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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

// Pantalla para tablets pequeñas o celulares plegados (ancho Mediano).
// Diferencia con la Compacta: aquí usamos una FILA (Row),
// con la imagen a la izquierda y el texto a la derecha.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMediana() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "TíoTec") })
        }
    ) { innerPadding ->
        // Row ordena sus hijos de izquierda a derecha.
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp), // espacio entre imagen y texto
            verticalAlignment = Alignment.CenterVertically       // centrados de arriba a abajo
        ) {
            // Lado izquierdo: la imagen.
            // weight(1f) significa "ocupa la mitad del ancho disponible".
            Image(
                painter = painterResource(id = R.drawable.logo_tiotec),
                contentDescription = "Logo de TíoTec",
                modifier = Modifier
                    .weight(1f)
                    .height(220.dp),
                contentScale = ContentScale.Fit
            )

            // Lado derecho: título, texto y botón, uno debajo del otro.
            Column(
                modifier = Modifier.weight(1f), // la otra mitad del ancho
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "¡Bienvenida a TíoTec!",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium // un poco más grande que en Compacta
                )

                Text(text = "Revisa tus hábitos de seguridad digital antes de que ocurra un problema.")

                Button(
                    onClick = { /* acción futura */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Nueva evaluación")
                }
            }
        }
    }
}

// Preview que simula una tablet pequeña de 700 x 900 dp.
@Preview(name = "Medium", widthDp = 700, heightDp = 900)
@Composable
fun PreviewMedium() {
    HomeScreenMediana()
}