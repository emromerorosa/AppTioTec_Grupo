package cl.duoc.apptiotec.ui.screens

import androidx.compose.runtime.mutableStateListOf
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Guarda en memoria las evaluaciones enviadas, para que el historial las muestre.
// Es un "object": hay una sola copia, compartida por todas las pantallas.
object HistorialEvaluaciones {

    // Lista observable: cuando cambia, la pantalla del historial se actualiza sola.
    // Parte con los 3 ejemplos que ya tenía la pantalla.
    val lista = mutableStateListOf(
        EvaluacionHistorial(1, "28 Oct 2026", "10/10 Respondidas", "Completado"),
        EvaluacionHistorial(2, "15 Oct 2026", "8/10 Respondidas", "Completado"),
        EvaluacionHistorial(3, "01 Oct 2026", "10/10 Respondidas", "Completado")
    )

    // Agrega una evaluación nueva arriba de la lista, con la fecha de hoy
    fun agregar(respondidas: Int, total: Int) {
        val fecha = SimpleDateFormat("dd MMM yyyy", Locale("es", "CL")).format(Date())
        lista.add(
            0,
            EvaluacionHistorial(lista.size + 1, fecha, "$respondidas/$total Respondidas", "Completado")
        )
    }
}