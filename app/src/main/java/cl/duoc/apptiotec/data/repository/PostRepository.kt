package cl.duoc.apptiotec.data.repository

import cl.duoc.apptiotec.data.remote.ApiService
import cl.duoc.apptiotec.data.remote.RetrofitInstance
import cl.duoc.apptiotec.model.Post

// La API llega como parámetro para poder reemplazarla por una falsa en las pruebas.
class PostRepository(
    private val api: ApiService = RetrofitInstance.api
) {
    suspend fun getPosts(): List<Post> = api.getPosts()
}