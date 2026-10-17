package cl.duoc.tiotec

import android.app.Application
import androidx.room.Room
import cl.duoc.tiotec.data.local.AppDatabase
import cl.duoc.tiotec.data.remote.RetrofitClient
import cl.duoc.tiotec.data.repository.TioTecRepository

class TioTecApp : Application() {
    // Inyección de dependencias manual (suficiente para un MVP).
    val repository: TioTecRepository by lazy {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "tiotec.db").build()
        TioTecRepository(db, RetrofitClient.api)
    }
}
