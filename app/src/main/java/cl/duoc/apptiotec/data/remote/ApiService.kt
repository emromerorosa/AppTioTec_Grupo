package cl.duoc.apptiotec.data.remote

import cl.duoc.apptiotec.model.Post
import cl.duoc.apptiotec.model.TareaPreventiva
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    // Tareas del checklist
    @GET("todos?_limit=6")
    suspend fun getTareas(): List<TareaPreventiva>

    @POST("todos")
    suspend fun crearTarea(@Body tarea: TareaPreventiva): TareaPreventiva

    @PUT("todos/{id}")
    suspend fun actualizarTarea(@Path("id") id: Int, @Body tarea: TareaPreventiva): TareaPreventiva

    @DELETE("todos/{id}")
    suspend fun eliminarTarea(@Path("id") id: Int)

    // Posts: pide la lista de posts a la API (GET /posts), solo 10
    @GET("posts?_limit=10")
    suspend fun getPosts(): List<Post>
}