package cl.duoc.apptiotec.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // API externa (jsonplaceholder): se usa solo para los posts
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // Microservicio propio (Spring Boot): se usa para las tareas del checklist.
    // 10.0.2.2 es la dirección que usa el emulador para llegar al localhost del computador.
    private const val BASE_URL_TAREAS = "http://172.20.10.6:8080/"

    // Conexión a la API externa (posts)
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Conexión al microservicio propio (tareas)
    val tareaApi: TareaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_TAREAS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TareaApiService::class.java)
    }
}