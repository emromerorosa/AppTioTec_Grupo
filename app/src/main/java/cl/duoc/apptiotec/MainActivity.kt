package cl.duoc.apptiotec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.apptiotec.ui.screens.HomeScreen
import cl.duoc.apptiotec.ui.screens.PerfilScreen
import cl.duoc.apptiotec.ui.screens.FormularioScreen
import cl.duoc.apptiotec.ui.screens.HistorialScreen
import cl.duoc.apptiotec.viewmodel.PerfilViewModel

class MainActivity : ComponentActivity() {

    private val perfilViewModel: PerfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        // Pantalla de Inicio
                        composable("home") {
                            HomeScreen(
                                onComenzarEvaluacion = { navController.navigate("evaluacion") },
                                onVerHistorial = { navController.navigate("historial") },
                                onIrConfiguracion = { navController.navigate("perfil") }
                            )
                        }

                        // Pantalla de Historial
                        composable("historial") {
                            HistorialScreen(
                                onVolver = { navController.popBackStack() }
                            )
                        }

                        // Pantalla de Perfil / Configuración (Cámara y Galería)
                        composable("perfil") {
                            PerfilScreen(
                                viewModel = perfilViewModel,
                                onContinuar = { navController.navigate("evaluacion") }
                            )
                        }

                        // Pantalla de Formulario de Evaluación
                        composable("evaluacion") {
                            FormularioScreen(
                                onVolver = { navController.popBackStack() },
                                onEnviarExitoso = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}