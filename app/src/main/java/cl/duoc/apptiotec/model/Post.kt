package cl.duoc.apptiotec.model

// Representa un post que viene desde la API
data class Post(
    val userId: Int,   // ID del usuario que creó el post
    val id: Int,       // ID del post
    val title: String, // Título del post
    val body: String   // Contenido del post
)