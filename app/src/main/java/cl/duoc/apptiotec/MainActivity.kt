package cl.duoc.apptiotec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.apptiotec.navigation.Screen
import cl.duoc.apptiotec.ui.FormularioScreen
import cl.duoc.apptiotec.ui.ResultadoScreen
import cl.duoc.apptiotec.ui.theme.AppTioTec_GrupoRJTheme
import cl.duoc.apptiotec.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTioTec_GrupoRJTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Formulario.route
                    ) {
                        composable(Screen.Formulario.route) {
                            val usuarioViewModel: UsuarioViewModel = viewModel()
                            FormularioScreen(
                                viewModel = usuarioViewModel,
                                onNavigateToResult = { nombre, correo, direccion ->
                                    navController.navigate(
                                        Screen.Resultado.createRoute(nombre, correo, direccion)
                                    )
                                }
                            )
                        }

                        composable(
                            route = Screen.Resultado.route,
                            arguments = listOf(
                                navArgument("nombre") { type = NavType.StringType },
                                navArgument("correo") { type = NavType.StringType },
                                navArgument("direccion") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                            val correo = backStackEntry.arguments?.getString("correo") ?: ""
                            val direccion = backStackEntry.arguments?.getString("direccion") ?: ""

                            ResultadoScreen(
                                nombre = nombre,
                                correo = correo,
                                direccion = direccion,
                                onVolverClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}