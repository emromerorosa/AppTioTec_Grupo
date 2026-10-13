package cl.duoc.apptiotec.navigation

// Sealed class para definir las rutas de navegación de forma segura
sealed class Screen(val route: String) {
    data object Home : Screen(route = "home_page")
    data object Profile : Screen(route = "profile_page")
    data object Settings : Screen(route = "settings_page")
}
