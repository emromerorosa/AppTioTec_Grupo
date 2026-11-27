package cl.duoc.apptiotec.data.remote

import cl.duoc.apptiotec.model.TareaPreventiva
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Rutas del microservicio propio (Spring Boot): http://IP-DEL-COMPUTADOR:8080/tareas
interface TareaApiService {

    // GET /tareas -> lista todas las tareas
    @GET("tareas")
    suspend fun getTareas(): List<TareaPreventiva>

    // POST /tareas -> crea una tarea nueva
    @POST("tareas")
    suspend fun crearTarea(@Body tarea: TareaPreventiva): TareaPreventiva

    // PUT /tareas/{id} -> actualiza una tarea existente
    @PUT("tareas/{id}")
    suspend fun actualizarTarea(@Path("id") id: Int, @Body tarea: TareaPreventiva): TareaPreventiva

    // DELETE /tareas/{id} -> borra una tarea.
    // El servidor responde sin contenido (204), por eso devuelve Response<Unit>
    @DELETE("tareas/{id}")
    suspend fun eliminarTarea(@Path("id") id: Int): Response<Unit>
}