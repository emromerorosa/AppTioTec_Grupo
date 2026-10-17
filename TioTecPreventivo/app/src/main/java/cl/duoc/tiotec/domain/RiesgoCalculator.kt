package cl.duoc.tiotec.domain

enum class Riesgo(val clave: String, val etiqueta: String) {
    ALTO("alto", "Riesgo alto"),
    MEDIO("medio", "Riesgo medio"),
    BAJO("bajo", "Riesgo bajo")
}

/** Único lugar donde se definen los umbrales de riesgo. */
object RiesgoCalculator {
    /** Cada respuesta vale 0, 1 o 2. Devuelve el porcentaje obtenido (0..100). */
    fun porcentaje(valores: List<Int>): Int =
        if (valores.isEmpty()) 0 else valores.sum() * 100 / (valores.size * 2)

    fun nivel(porcentaje: Int): Riesgo = when {
        porcentaje < 40 -> Riesgo.ALTO
        porcentaje <= 70 -> Riesgo.MEDIO
        else -> Riesgo.BAJO
    }
}
