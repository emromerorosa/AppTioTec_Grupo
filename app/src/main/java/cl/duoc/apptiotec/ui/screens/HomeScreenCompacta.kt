package cl.duoc.apptiotec.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.duoc.apptiotec.R

// Pantalla para celulares (ancho Compacto).
// Todo va en UNA sola columna, uno debajo del otro.

// TopAppBar todavía es "experimental", por eso se agrega esta anotación.
@OptIn(ExperimentalMaterial3Api::class)
// @Composable indica que esta función dibuja una pantalla.
@Composable
fun HomeScreenCompacta() {
    // Scaffold es la estructura base: barra de arriba + contenido.
    Scaffold(
        topBar = {
            // Barra superior con el título de la app.
            TopAppBar(title = { Text(text = "TíoTec") })
        }
    ) { innerPadding ->
        // Column ordena sus hijos de arriba hacia abajo.
        Column(
            modifier = Modifier
                .padding(innerPadding)   // evita que el contenido quede bajo la barra
                .fillMaxSize()           // ocupa toda la pantalla
                .padding(16.dp),         // margen de 16 dp alrededor
            verticalArrangement = Arrangement.spacedBy(20.dp) // espacio entre elementos
        ) {
            // Título de bienvenida, con el color principal del tema.
            Text(
                text = "¡Bienvenida a TíoTec!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            // Texto explicativo.
            Text(text = "Revisa tus hábitos de seguridad digital antes de que ocurra un problema.")

            // Botón. Por ahora no hace nada: onClick queda vacío.
            Button(onClick = { /* acción futura */ }) {
                Text(text = "Nueva evaluación")
            }

            // Imagen que guardamos en res/drawable.
            Image(
                painter = painterResource(id = R.drawable.logo_tiotec),
                contentDescription = "Logo de TíoTec", // texto para accesibilidad
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit // ajusta la imagen sin deformarla
            )
        }
    }
}

// Preview: permite ver la pantalla en Android Studio sin ejecutar la app.
// Simula un celular de 360 x 800 dp.
@Preview(name = "Compact", widthDp = 360, heightDp = 800)
@Composable
fun PreviewCompact() {
    HomeScreenCompacta()
}