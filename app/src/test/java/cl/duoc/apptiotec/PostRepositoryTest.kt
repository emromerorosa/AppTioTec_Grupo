package cl.duoc.apptiotec

import cl.duoc.apptiotec.data.remote.ApiService
import cl.duoc.apptiotec.data.repository.PostRepository
import cl.duoc.apptiotec.model.Post
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PostRepositoryTest {

    @Test
    fun `getPosts devuelve la lista de la API`() = runTest {
        // API falsa que devuelve un post
        val api = mockk<ApiService>()
        val post = Post(userId = 1, id = 1, title = "titulo", body = "cuerpo")
        coEvery { api.getPosts() } returns listOf(post)

        val repository = PostRepository(api)
        val resultado = repository.getPosts()

        assertEquals(listOf(post), resultado)
    }
}