package cl.duoc.apptiotec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cl.duoc.apptiotec.ui.screens.HomeScreen
import cl.duoc.apptiotec.ui.screens.HomeScreenCompacta
import cl.duoc.apptiotec.ui.theme.AppTioTec_GrupoRJTheme

// MainActivity es la "puerta de entrada" de la app: lo primero que se ejecuta.
class MainActivity : ComponentActivity() {
    // onCreate se ejecuta cuando la app se abre.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Permite que la app use toda la pantalla, incluso detrás de las barras del sistema.
        enableEdgeToEdge()
        // setContent define lo que se ve en pantalla.
        setContent {
            // El tema aplica los colores y tipografías de la app.
            AppTioTec_GrupoRJTheme {
                // Mostramos nuestra pantalla adaptable.
                HomeScreen()
            }
        }
    }
}

// Preview para ver la pantalla en Android Studio sin ejecutar la app.
// Usamos la pantalla Compacta porque HomeScreen necesita una app real en marcha
// para medir el tamaño, y la vista previa no la tiene.
@Preview(showBackground = true)
@Composable
fun MainPreview() {
    AppTioTec_GrupoRJTheme {
        HomeScreenCompacta()
    }
}