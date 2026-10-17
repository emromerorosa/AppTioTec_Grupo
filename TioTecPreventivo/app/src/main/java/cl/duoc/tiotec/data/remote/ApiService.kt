package cl.duoc.tiotec.data.remote

import cl.duoc.tiotec.data.local.Categoria
import cl.duoc.tiotec.data.local.Pregunta
import cl.duoc.tiotec.data.local.Recomendacion
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Para el MVP, la API devuelve los mismos campos que las entidades de Room,
// así que se reutilizan como modelos. Si tu API cambia los nombres, crea DTOs aparte.
interface TioTecApi {
    @GET("categorias") suspend fun categorias(): List<Categoria>
    @GET("preguntas") suspend fun preguntas(): List<Pregunta>
    @GET("recomendaciones") suspend fun recomendaciones(): List<Recomendacion>
    // Agrega aquí: perfiles, evaluaciones, checklist, solicitudes (ver lista de endpoints).
}

object RetrofitClient {
    // TODO: reemplazar por la URL de la API de prueba (emulador: 10.0.2.2 apunta a tu PC).
    private const val BASE_URL = "http://10.0.2.2:3000/"

    val api: TioTecApi by lazy {
        val http = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(http)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TioTecApi::class.java)
    }
}
