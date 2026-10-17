package cl.duoc.tiotec.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import cl.duoc.tiotec.data.repository.TioTecRepository
import cl.duoc.tiotec.ui.evaluacion.EvaluacionScreen
import cl.duoc.tiotec.ui.evaluacion.EvaluacionViewModel
import cl.duoc.tiotec.ui.home.HomeScreen

object Rutas {
    const val INICIO = "inicio"
    const val CHECKLIST = "checklist"
    const val HISTORIAL = "historial"
    const val MAS = "mas"
    const val EVALUACION = "evaluacion"
    const val RESULTADOS = "resultados/{evaluacionId}"
    fun resultados(id: Int) = "resultados/$id"
}

private data class Destino(val ruta: String, val titulo: String, val icono: ImageVector)

private val destinos = listOf(
    Destino(Rutas.INICIO, "Inicio", Icons.Default.Home),
    Destino(Rutas.CHECKLIST, "Checklist", Icons.Default.CheckCircle),
    Destino(Rutas.HISTORIAL, "Historial", Icons.Default.History),
    Destino(Rutas.MAS, "Más", Icons.Default.MoreHoriz)
)

@Composable
fun AppNavHost(repo: TioTecRepository) {
    val nav = rememberNavController()
    val actual by nav.currentBackStackEntryAsState()
    val rutaActual = actual?.destination?.route

    // TODO: leer el perfil real desde repo.perfil; si no existe, mostrar "Crear perfil".
    val perfilId = 1
    val nombre = "Invitada"

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinos.forEach { d ->
                    NavigationBarItem(
                        selected = rutaActual == d.ruta,
                        onClick = {
                            nav.navigate(d.ruta) {
                                popUpTo(nav.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(d.icono, contentDescription = d.titulo) },
                        label = { Text(d.titulo) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(nav, startDestination = Rutas.INICIO, modifier = Modifier.padding(padding)) {
            composable(Rutas.INICIO) {
                HomeScreen(nombre) { nav.navigate(Rutas.EVALUACION) }
            }
            composable(Rutas.EVALUACION) {
                val vm: EvaluacionViewModel = viewModel(factory = EvaluacionViewModel.Factory(repo))
                EvaluacionScreen(vm, perfilId) { evalId -> nav.navigate(Rutas.resultados(evalId)) }
            }
            composable(Rutas.RESULTADOS) { entry ->
                val id = entry.arguments?.getString("evaluacionId")
                Text("Resultados de la evaluación $id (pendiente)") // TODO: ResultadosScreen
            }
            composable(Rutas.CHECKLIST) { Text("Checklist (pendiente)") }
            composable(Rutas.HISTORIAL) { Text("Historial (pendiente)") }
            composable(Rutas.MAS) { Text("Dispositivos, Aprende, Asistencia, Ajustes (pendiente)") }
        }
    }
}
