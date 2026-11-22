package cl.duoc.apptiotec

import android.graphics.Bitmap
import android.net.Uri
import cl.duoc.apptiotec.viewmodel.PerfilViewModel
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class PerfilViewModelTest {

    private lateinit var viewModel: PerfilViewModel

    // Uri y Bitmap falsos: en pruebas unitarias no existe el Android real
    private val uriFalsa = mockk<Uri>()
    private val bitmapFalso = mockk<Bitmap>()

    @Before
    fun setUp() {
        viewModel = PerfilViewModel()
    }

    @Test
    fun `al iniciar no hay imagen ni foto`() {
        assertNull(viewModel.imagenUri.value)
        assertNull(viewModel.bitmapFoto.value)
    }

    @Test
    fun `setImagenUri guarda la uri`() {
        viewModel.setImagenUri(uriFalsa)

        assertEquals(uriFalsa, viewModel.imagenUri.value)
        assertNull(viewModel.bitmapFoto.value)
    }

    @Test
    fun `setBitmapFoto guarda la foto`() {
        viewModel.setBitmapFoto(bitmapFalso)

        assertEquals(bitmapFalso, viewModel.bitmapFoto.value)
        assertNull(viewModel.imagenUri.value)
    }

    @Test
    fun `al elegir una uri se borra la foto anterior`() {
        viewModel.setBitmapFoto(bitmapFalso)

        viewModel.setImagenUri(uriFalsa)

        assertNull(viewModel.bitmapFoto.value)
        assertEquals(uriFalsa, viewModel.imagenUri.value)
    }

    @Test
    fun `al sacar una foto se borra la uri anterior`() {
        viewModel.setImagenUri(uriFalsa)

        viewModel.setBitmapFoto(bitmapFalso)

        assertNull(viewModel.imagenUri.value)
        assertEquals(bitmapFalso, viewModel.bitmapFoto.value)
    }
}