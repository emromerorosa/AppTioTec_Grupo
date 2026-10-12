package cl.duoc.apptiotec.ui.utils

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

// Esta función le pregunta a Android qué tan grande es la pantalla.
// La devuelve como un "WindowSizeClass", que puede ser Compact, Medium o Expanded.
// Se crea una sola vez aquí para reutilizarla en toda la app.

// Esta anotación avisa que usamos una herramienta marcada como "experimental".
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
// @Composable indica que la función se usa dentro de pantallas hechas con Compose.
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {
    // LocalActivity.current entrega la pantalla (Activity) donde corre la app.
    // calculateWindowSizeClass mide esa pantalla y la clasifica por tamaño.
    return calculateWindowSizeClass(LocalActivity.current as Activity)
}
