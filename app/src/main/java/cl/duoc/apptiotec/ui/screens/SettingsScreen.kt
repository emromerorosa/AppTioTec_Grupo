package cl.duoc.apptiotec.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.apptiotec.navigation.Screen
import cl.duoc.apptiotec.viewmodel.MainViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla de Configuración (Settings)")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { viewModel.navigateTo(Screen.Home) }) {
            Text(text = "Volver al Inicio")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.navigateTo(Screen.Profile) }) {
            Text(text = "Ir al Perfil")
        }
    }
}