package cl.duoc.apptiotec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.apptiotec.navigation.NavigationEvent
import cl.duoc.apptiotec.navigation.Screen
import cl.duoc.apptiotec.ui.screens.HomeScreen
import cl.duoc.apptiotec.ui.screens.ProfileScreen
import cl.duoc.apptiotec.ui.screens.SettingsScreen
import cl.duoc.apptiotec.ui.theme.AppTioTec_GrupoRJTheme
import cl.duoc.apptiotec.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTioTec_GrupoRJTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MainViewModel = viewModel()
                    val navController = rememberNavController()

                    // Escuchar eventos de navegación reactivos
                    LaunchedEffect(key1 = Unit) {
                        viewModel.navigationEvents.collectLatest { event ->
                            when (event) {
                                is NavigationEvent.NavigateTo -> {
                                    navController.navigate(event.route.route) {
                                        event.popupToRoute?.let {
                                            popUpTo(it.route) {
                                                inclusive = event.inclusive
                                            }
                                        }
                                        launchSingleTop = event.singleTop
                                    }
                                }
                                is NavigationEvent.PopBackStack -> navController.popBackStack()
                                is NavigationEvent.NavigateUp -> navController.navigateUp()
                            }
                        }
                    }

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Home.route) {
                                HomeScreen(navController = navController, viewModel = viewModel)
                            }
                            composable(Screen.Profile.route) {
                                ProfileScreen(navController = navController, viewModel = viewModel)
                            }
                            composable(Screen.Settings.route) {
                                SettingsScreen(navController = navController, viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}