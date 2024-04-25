package inc.blubz.fitsync.ux.directory

import app.cash.turbine.turbineScope
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.blubz.fitsync.ux.mockIndividualRepository
import org.blubz.fitsync.model.domain.inline.IndividualId
import inc.blubz.fitsync.ui.navigation.NavigationAction
import org.blubz.fitsync.ux.individual.IndividualRoute
import org.blubz.fitsync.ux.individualedit.IndividualEditRoute
import org.blubz.fitsync.ux.settings.SettingsRoute
import kotlin.test.Test

class GetDirectoryUiStateUseCaseTest {
    @Test
    fun testNavigation() = runTest {
        turbineScope {
            val useCase = GetDirectoryUiStateUseCase(mockIndividualRepository())
            val stateScope = CoroutineScope(Job())
            val lastNavigationActionFlow = MutableStateFlow<NavigationAction?>(null)
            val navigationActionTurbine = lastNavigationActionFlow.testIn(stateScope)
            navigationActionTurbine.awaitItem() // consume default value

            val uiState = useCase(
                coroutineScope = stateScope,
                navigate = { lastNavigationActionFlow.value = it }
            )

            uiState.onNewClicked()
            assertThat(navigationActionTurbine.awaitItem()).isEqualTo(NavigationAction.Navigate(IndividualEditRoute.createRoute()))

            uiState.onIndividualClicked(IndividualId("1"))
            assertThat(navigationActionTurbine.awaitItem()).isEqualTo(NavigationAction.Navigate(IndividualRoute.createRoute(IndividualId("1"))))

            uiState.onSettingsClicked()
            assertThat(navigationActionTurbine.awaitItem()).isEqualTo(NavigationAction.Navigate(SettingsRoute.createRoute()))

            stateScope.cancel()
        }
    }

    @Test
    fun testDirectoryList() = runTest {
        turbineScope {
            val useCase = GetDirectoryUiStateUseCase(mockIndividualRepository())
            val stateScope = CoroutineScope(Job())

            val uiState = useCase(
                coroutineScope = stateScope,
                navigate = { }
            )

            val directoryListTurbine = uiState.directoryListFlow.testIn(stateScope)

            val initialList = directoryListTurbine.awaitItem()
            assertThat(initialList).isEmpty() // default value

            val list = directoryListTurbine.awaitItem()
            assertThat(list[0].individualId).isEqualTo(IndividualId("1"))
            assertThat(list[1].individualId).isEqualTo(IndividualId("2"))

            stateScope.cancel()
        }
    }
}
