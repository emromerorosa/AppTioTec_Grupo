package cl.duoc.apptiotec.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PerfilViewModel : ViewModel() {

    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    private val _bitmapFoto = MutableStateFlow<Bitmap?>(null)
    val bitmapFoto: StateFlow<Bitmap?> = _bitmapFoto.asStateFlow()

    fun setImagenUri(uri: Uri?) {
        _imagenUri.value = uri
        _bitmapFoto.value = null
    }

    fun setBitmapFoto(bitmap: Bitmap?) {
        _bitmapFoto.value = bitmap
        _imagenUri.value = null
    }
}