package cl.duoc.apptiotec

import cl.duoc.apptiotec.data.remote.TareaApiService
import cl.duoc.apptiotec.data.repository.TareaRepository
import cl.duoc.apptiotec.model.TareaPreventiva
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TareaRepositoryTest {

    // API falsa del microservicio: no usa internet
    private lateinit var api: TareaApiService
    private lateinit var repository: TareaRepository

    private val tarea = TareaPreventiva(id = 5, title = "Probar", completed = false)

    @Before
    fun setUp() {
        api = mockk(relaxed = true)
        repository = TareaRepository(api)
    }

    @Test
    fun `obtenerTareas devuelve la lista de la API`() = runTest {
        coEvery { api.getTareas() } returns listOf(tarea)

        val resultado = repository.obtenerTareas()

        assertEquals(listOf(tarea), resultado)
    }

    @Test
    fun `crearTarea llama a la API con la tarea`() = runTest {
        coEvery { api.crearTarea(tarea) } returns tarea

        repository.crearTarea(tarea)

        coVerify { api.crearTarea(tarea) }
    }

    @Test
    fun `actualizarTarea usa el id de la tarea`() = runTest {
        coEvery { api.actualizarTarea(5, tarea) } returns tarea

        repository.actualizarTarea(tarea)

        coVerify { api.actualizarTarea(5, tarea) }
    }

    @Test
    fun `eliminarTarea llama a la API con el id`() = runTest {
        repository.eliminarTarea(5)

        coVerify { api.eliminarTarea(5) }
    }
}