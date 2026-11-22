package cl.duoc.apptiotec

import cl.duoc.apptiotec.navigation.NavigationEvent
import cl.duoc.apptiotec.navigation.Screen
import cl.duoc.apptiotec.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @Before
    fun setUp() {
        // El ViewModel usa Dispatchers.Main, que no existe en pruebas.
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `navigateTo emite un evento NavigateTo con la pantalla`() = runTest {
        val viewModel = MainViewModel()
        val eventos = mutableListOf<NavigationEvent>()
        // Empezamos a escuchar ANTES de emitir (el flujo no guarda eventos pasados)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.navigationEvents.toList(eventos)
        }

        viewModel.navigateTo(Screen.Checklist)

        assertEquals(1, eventos.size)
        val evento = eventos[0] as NavigationEvent.NavigateTo
        assertEquals(Screen.Checklist, evento.route)
    }

    @Test
    fun `navigateBack emite PopBackStack`() = runTest {
        val viewModel = MainViewModel()
        val eventos = mutableListOf<NavigationEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.navigationEvents.toList(eventos)
        }

        viewModel.navigateBack()

        assertEquals(1, eventos.size)
        assertTrue(eventos[0] is NavigationEvent.PopBackStack)
    }

    @Test
    fun `navigateUp emite NavigateUp`() = runTest {
        val viewModel = MainViewModel()
        val eventos = mutableListOf<NavigationEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.navigationEvents.toList(eventos)
        }

        viewModel.navigateUp()

        assertEquals(1, eventos.size)
        assertTrue(eventos[0] is NavigationEvent.NavigateUp)
    }
}