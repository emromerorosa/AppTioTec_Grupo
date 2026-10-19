package cl.duoc.apptiotec.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import cl.duoc.apptiotec.ui.components.ImagenInteligente
import cl.duoc.apptiotec.viewmodel.PerfilViewModel
import java.io.File

@Composable
fun PerfilScreen(viewModel: PerfilViewModel) {
    val imagenUri by viewModel.imagenUri.collectAsState()
    val context = LocalContext.current

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher para Galería
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.actualizarImagen(it) }
    }

    // Launcher para Tomar Foto
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exitoso: Boolean ->
        if (exitoso && tempCameraUri != null) {
            viewModel.actualizarImagen(tempCameraUri)
        }
    }

    // Launcher para solicitar Permiso de Cámara
    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido: Boolean ->
        if (concedido) {
            val uri = crearArchivoTemporalUri(context)
            tempCameraUri = uri
            camaraLauncher.launch(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenInteligente(imagenUri = imagenUri)

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { galeriaLauncher.launch("image/*") }) {
                Text("Galería")
            }

            OutlinedButton(onClick = {
                val tienePermiso = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED

                if (tienePermiso) {
                    val uri = crearArchivoTemporalUri(context)
                    tempCameraUri = uri
                    camaraLauncher.launch(uri)
                } else {
                    permisoCamaraLauncher.launch(Manifest.permission.CAMERA)
                }
            }) {
                Text("Cámara")
            }
        }
    }
}

private fun crearArchivoTemporalUri(context: Context): Uri {
    val file = File.createTempFile("selected_image_", ".jpg", context.cacheDir)
    val authority = "${context.packageName}.fileprovider"
    return FileProvider.getUriForFile(context, authority, file)
}