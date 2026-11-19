package cl.duoc.apptiotec.data.repository

import cl.duoc.apptiotec.data.remote.RetrofitInstance
import cl.duoc.apptiotec.model.Post

// Este repositorio se encarga de pedir los posts a la API usando Retrofit
class PostRepository {

    // Función que obtiene los posts desde la API
    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}