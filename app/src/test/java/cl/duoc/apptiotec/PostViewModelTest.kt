package cl.duoc.apptiotec

import cl.duoc.apptiotec.data.repository.PostRepository
import cl.duoc.apptiotec.model.Post
import cl.duoc.apptiotec.viewmodel.PostViewModel
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
class PostViewModelTest {

    // Repositorio falso: no usa internet, devuelve lo que nosotros le digamos
    private lateinit var repository: PostRepository

    @Before
    fun setUp() {
        // El ViewModel usa Dispatchers.Main, que no existe en pruebas.
        // Lo reemplazamos por uno de prueba.
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = mockk()
    }

    @After
    fun tearDown() {
        // Dejamos todo como estaba
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando la API responde, los posts quedan en espanol`() {
        // El repositorio falso devuelve 2 posts en inglés
        val listaFalsa = listOf(
            Post(userId = 1, id = 1, title = "title en ingles", body = "body en ingles"),
            Post(userId = 1, id = 2, title = "otro title", body = "otro body")
        )
        coEvery { repository.getPosts() } returns listaFalsa

        // Al crear el ViewModel se cargan los posts solos (init)
        val viewModel = PostViewModel(repository)

        // Se reemplazaron por los textos en español
        val posts = viewModel.posts.value
        assertEquals(2, posts.size)
        assertEquals("Cómo reconocer un correo falso", posts[0].title)
        assertEquals("Por qué conviene hacer respaldos", posts[1].title)
    }

    @Test
    fun `cuando la API falla, la lista queda vacia`() {
        // El repositorio falso lanza un error, como si no hubiera internet
        coEvery { repository.getPosts() } throws RuntimeException("Sin internet")

        val viewModel = PostViewModel(repository)

        assertTrue(viewModel.posts.value.isEmpty())
    }
}