package cl.duoc.apptiotec.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import cl.duoc.apptiotec.ui.utils.obtenerWindowSizeClass

// Esta es la pantalla "principal". No dibuja nada por sí misma:
// solo mira el tamaño de la pantalla y llama a la versión que corresponde.
@Composable
fun HomeScreen() {
    // Pedimos el tamaño de la pantalla (función que creamos en WindowSizeUtils).
    val windowSizeClass = obtenerWindowSizeClass()

    // "when" funciona como un "si... entonces..." con varias opciones.
    // Revisamos el ANCHO de la pantalla (widthSizeClass).
    when (windowSizeClass.widthSizeClass) {
        // Pantalla angosta (celular): diseño Compacto.
        WindowWidthSizeClass.Compact -> HomeScreenCompacta()

        // Pantalla mediana (tablet pequeña o celular plegado): diseño Mediano.
        WindowWidthSizeClass.Medium -> HomeScreenMediana()

        // Cualquier otro caso es una pantalla grande (Expanded): diseño Expandido.
        else -> HomeScreenExpandida()
    }
}