package cl.duoc.apptiotec

import cl.duoc.apptiotec.data.repository.TareaRepository
import cl.duoc.apptiotec.model.TareaPreventiva
import cl.duoc.apptiotec.viewmodel.ChecklistViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChecklistViewModelTest {

    // Repositorio falso. "relaxed" = las funciones que no configuremos
    // (crear, actualizar, eliminar) responden sin error.
    private lateinit var repository: TareaRepository

    // 6 tareas falsas, como las que manda la API
    private val tareasFalsas = (1..6).map {
        TareaPreventiva(id = it, title = "title $it", completed = false)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = mockk(relaxed = true)
        coEvery { repository.obtenerTareas() } returns tareasFalsas
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `al iniciar carga 6 tareas en espanol y el progreso es 0`() {
        val viewModel = ChecklistViewModel(repository)

        assertEquals(6, viewModel.tareas.value.size)
        assertEquals(
            "Nunca entregar claves ni códigos que lleguen por mensaje de texto",
            viewModel.tareas.value[0].title
        )
        assertEquals(0f, viewModel.progreso.value, 0.001f)
    }

    @Test
    fun `al marcar una tarea el progreso sube a 1 de 6`() {
        val viewModel = ChecklistViewModel(repository)

        viewModel.cambiarEstadoTarea(viewModel.tareas.value[0])

        assertTrue(viewModel.tareas.value[0].completed)
        assertEquals(1f / 6f, viewModel.progreso.value, 0.001f)
    }

    @Test
    fun `al eliminar una tarea quedan 5`() {
        val viewModel = ChecklistViewModel(repository)

        viewModel.eliminarTarea(3)

        assertEquals(5, viewModel.tareas.value.size)
        assertTrue(viewModel.tareas.value.none { it.id == 3 })
    }

    @Test
    fun `al agregar una tarea el id es el maximo mas 1`() {
        val viewModel = ChecklistViewModel(repository)

        viewModel.agregarTarea("Cambiar mi clave del correo")

        assertEquals(7, viewModel.tareas.value.size)
        assertEquals(7, viewModel.tareas.value.last().id)
    }

    @Test
    fun `si el titulo esta vacio no se agrega nada`() {
        val viewModel = ChecklistViewModel(repository)

        viewModel.agregarTarea("   ")

        assertEquals(6, viewModel.tareas.value.size)
    }
}