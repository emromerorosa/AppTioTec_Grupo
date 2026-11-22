package cl.duoc.apptiotec

import cl.duoc.apptiotec.data.UserPreferences
import cl.duoc.apptiotec.viewmodel.UsuarioViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    // Preferencias falsas: no usan el almacenamiento real del teléfono
    private lateinit var preferences: UserPreferences
    private lateinit var viewModel: UsuarioViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        preferences = mockk(relaxed = true)
        every { preferences.userName } returns flowOf("")
        viewModel = UsuarioViewModel(preferences)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `el formulario vacio no es valido`() {
        assertFalse(viewModel.esFormularioValido)
    }

    @Test
    fun `el formulario con datos correctos es valido`() {
        viewModel.nombre = "Rosa"
        viewModel.correo = "rosa@correo.cl"
        viewModel.direccion = "Alameda 123"

        assertTrue(viewModel.esFormularioValido)
    }

    @Test
    fun `un correo sin arroba hace invalido el formulario`() {
        viewModel.nombre = "Rosa"
        viewModel.correo = "rosa.correo.cl"
        viewModel.direccion = "Alameda 123"

        assertFalse(viewModel.esFormularioValido)
    }

    @Test
    fun `guardarUsuario guarda el nombre cuando el formulario es valido`() {
        viewModel.nombre = "Rosa"
        viewModel.correo = "rosa@correo.cl"
        viewModel.direccion = "Alameda 123"

        viewModel.guardarUsuario()

        coVerify { preferences.saveUserName("Rosa") }
    }

    @Test
    fun `guardarUsuario no guarda nada si el formulario es invalido`() {
        viewModel.guardarUsuario()

        coVerify(exactly = 0) { preferences.saveUserName(any()) }
    }

    @Test
    fun `limpiarFormulario deja los campos vacios`() {
        viewModel.nombre = "Rosa"
        viewModel.correo = "rosa@correo.cl"
        viewModel.direccion = "Alameda 123"

        viewModel.limpiarFormulario()

        assertEquals("", viewModel.nombre)
        assertEquals("", viewModel.correo)
        assertEquals("", viewModel.direccion)
    }

    @Test
    fun `nombreGuardado muestra el nombre que entrega DataStore`() = runTest {
        every { preferences.userName } returns flowOf("Rosa")
        val vm = UsuarioViewModel(preferences)

        // Hay que "suscribirse" para que el flujo empiece a entregar valores
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            vm.nombreGuardado.collect { }
        }

        assertEquals("Rosa", vm.nombreGuardado.value)
    }
}