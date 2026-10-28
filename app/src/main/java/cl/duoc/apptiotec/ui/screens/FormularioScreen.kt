package cl.duoc.apptiotec.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    onVolver: () -> Unit = {},
    onEnviarExitoso: () -> Unit = {}
) {
    val context = LocalContext.current
    val respuestas = remember { mutableStateMapOf<Int, String>() }

    val preguntas = listOf(
        PreguntaItem(1, "Actualizaciones", "¿Mantienes actualizado el sistema y las aplicaciones?"),
        PreguntaItem(2, "Actualizaciones", "¿Instalas aplicaciones solo desde la tienda oficial?"),
        PreguntaItem(3, "Contraseñas", "¿Usas una contraseña distinta en cada cuenta?"),
        PreguntaItem(4, "Contraseñas", "¿Tienes activada la verificación en dos pasos?"),
        PreguntaItem(5, "Redes", "¿Evitas conectarte a redes Wi-Fi públicas sin VPN?"),
        PreguntaItem(6, "Redes", "¿Verificas la autenticidad de los enlaces antes de hacer clic?"),
        PreguntaItem(7, "Seguridad", "¿Cuentas con un antivirus o antimalware activo?"),
        PreguntaItem(8, "Seguridad", "¿Realizas respaldos periódicos de tus archivos importantes?"),
        PreguntaItem(9, "Privacidad", "¿Revisas los permisos que solicitan las aplicaciones?"),
        PreguntaItem(10, "Privacidad", "¿Bloqueas la pantalla de tu dispositivo al ausentarte?")
    )

    val totalPreguntas = preguntas.size
    val respondidas = respuestas.size
    val progreso = if (totalPreguntas > 0) respondidas.toFloat() / totalPreguntas.toFloat() else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Evaluación", fontSize = 22.sp, fontWeight = FontWeight.Normal) },
                navigationIcon = {
                    IconButton(onClick = { onVolver() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF6EEFF))
            )
        },
        containerColor = Color(0xFFFBF8FF)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            LinearProgressIndicator(
                progress = { progreso },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Color(0xFF6750A4),
                trackColor = Color(0xFFE8DEF8)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$respondidas de $totalPreguntas respondidas",
                fontSize = 14.sp,
                color = Color(0xFF49454F),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            preguntas.forEach { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = item.categoria, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6750A4))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = item.texto, fontSize = 16.sp, color = Color(0xFF1D1B20), lineHeight = 22.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            listOf("Sí", "A veces", "No").forEach { opcion ->
                                val isSeleccionado = respuestas[item.id] == opcion
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isSeleccionado) Color(0xFF6750A4) else Color(0xFFF3EDF7))
                                        .border(1.dp, if (isSeleccionado) Color(0xFF6750A4) else Color(0xFF79747E), RoundedCornerShape(12.dp))
                                        .clickable { respuestas[item.id] = opcion }
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = opcion,
                                        fontSize = 14.sp,
                                        color = if (isSeleccionado) Color.White else Color(0xFF49454F),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "Evaluación enviada con éxito", Toast.LENGTH_SHORT).show()
                    onEnviarExitoso()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
                shape = RoundedCornerShape(24.dp),
                enabled = respondidas > 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Enviar evaluación", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

data class PreguntaItem(val id: Int, val categoria: String, val texto: String)