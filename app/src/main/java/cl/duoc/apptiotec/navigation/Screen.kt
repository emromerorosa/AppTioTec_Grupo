package cl.duoc.apptiotec.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object Formulario : Screen("formulario")
    object Resultado : Screen("resultado/{nombre}/{correo}/{direccion}") {
        fun createRoute(nombre: String, correo: String, direccion: String) =
            "resultado/$nombre/$correo/$direccion"
    }
}