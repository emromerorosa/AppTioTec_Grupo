package cl.duoc.apptiotec.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.apptiotec.data.repository.PostRepository
import cl.duoc.apptiotec.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel que guarda la lista de posts que llegan de la API.
// El repositorio llega como parámetro para poder reemplazarlo por uno falso en las pruebas.
// Por defecto usa el real, así la app funciona igual que antes.
class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    // Títulos en español: reemplazan los textos de relleno de la API
    private val titulosEnEspanol = listOf(
        "Cómo reconocer un correo falso",
        "Por qué conviene hacer respaldos",
        "Mantén tu celular actualizado",
        "Activa la verificación en dos pasos",
        "Cuida tus claves",
        "Cuidado con las redes Wi-Fi públicas",
        "Qué hacer si pierdes tu celular",
        "Cómo recuperar una cuenta robada",
        "No compartas códigos por mensaje",
        "Revisa los permisos de tus aplicaciones"
    )

    // Contenidos en español, uno por cada título
    private val contenidosEnEspanol = listOf(
        "Desconfía de los correos que piden datos urgentes o traen enlaces extraños. Ante la duda, no los abras.",
        "Guardar una copia de tus fotos y archivos te permite recuperarlos si pierdes el equipo.",
        "Las actualizaciones corrigen fallas de seguridad. Instálalas apenas estén disponibles.",
        "Además de tu clave, la app te pide un código extra. Así es más difícil que otra persona entre a tu cuenta.",
        "Usa una clave distinta para cada cuenta y que sea difícil de adivinar. No la compartas con nadie.",
        "En una red Wi-Fi pública evita entrar al banco o ingresar claves, porque otras personas podrían ver tus datos.",
        "Bloquea el equipo, cambia tus claves importantes y avisa a tu compañía telefónica.",
        "Usa la opción de recuperación de la aplicación y cambia tu clave apenas recuperes el acceso.",
        "Nadie serio te pedirá un código que llega por mensaje. Si alguien lo pide, es un intento de engaño.",
        "Una app de linterna no necesita ver tus contactos. Quita los permisos que no tengan sentido."
    )

    // Lista que se puede modificar (solo la usa el ViewModel)
    private val _posts = MutableStateFlow<List<Post>>(emptyList())

    // Lista pública de solo lectura (la pantalla la observa)
    val posts: StateFlow<List<Post>> = _posts

    // Se ejecuta automáticamente al crear el ViewModel
    init {
        cargarPosts()
    }

    // Pide los posts a la API y cambia el texto por uno en español
    private fun cargarPosts() {
        viewModelScope.launch {
            try {
                val listaApi = repository.getPosts()
                val listaEnEspanol = listaApi.mapIndexed { index, post ->
                    post.copy(
                        title = titulosEnEspanol.getOrElse(index) { "Consejo de seguridad digital" },
                        body = contenidosEnEspanol.getOrElse(index) { "Revisa periódicamente tus medidas de seguridad." }
                    )
                }
                _posts.value = listaEnEspanol
            } catch (e: Exception) {
                println("Error al obtener posts: ${e.localizedMessage}")
            }
        }
    }
}