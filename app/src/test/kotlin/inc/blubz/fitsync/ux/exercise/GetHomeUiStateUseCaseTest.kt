package inc.blubz.fitsync.ux.exercise

import app.cash.turbine.turbineScope
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import inc.blubz.fitsync.ui.compose.dialog.MessageDialogUiState
import inc.blubz.fitsync.ui.navigation.NavigationAction
import inc.blubz.fitsync.ux.TestIndividuals
import inc.blubz.fitsync.ux.individualedit.IndividualEditRoute
import inc.blubz.fitsync.ux.mockIndividualRepository
import kotlin.test.Test

class GetHomeUiStateUseCaseTest {
    @Test
    fun testNavigation() = runTest {
        turbineScope {
            val useCase = GetIndividualUiStateUseCase(mockIndividualRepository(), mockk(relaxed = true))
            val stateScope = CoroutineScope(Job())
            val lastNavigationActionFlow = MutableStateFlow<NavigationAction?>(null)
            val navigationActionTurbine = lastNavigationActionFlow.testIn(stateScope)
            navigationActionTurbine.awaitItem() // consume default value

            val selectedIndividualId = TestIndividuals.individualId1
            val uiState = useCase(
                individualId = selectedIndividualId,
                coroutineScope = stateScope,
                navigate = { lastNavigationActionFlow.value = it }
            )

            val dialogUiStateFlowTurbine = uiState.dialogUiStateFlow.testIn(stateScope)
            dialogUiStateFlowTurbine.awaitItem() // consume default value

            uiState.onEditClick()
            assertThat(navigationActionTurbine.awaitItem()).isEqualTo(NavigationAction.Navigate(IndividualEditRoute.createRoute(selectedIndividualId)))

            uiState.onDeleteClick()
            val deleteDialog = dialogUiStateFlowTurbine.awaitItem()
            if (deleteDialog is MessageDialogUiState) {
                deleteDialog.onConfirm?.invoke(Unit)
            } else {
                error("Expected error Delete MessageDialogUiState")
            }

            assertThat(navigationActionTurbine.awaitItem()).isEqualTo(NavigationAction.Pop())

            navigationActionTurbine.cancelAndIgnoreRemainingEvents()
            dialogUiStateFlowTurbine.cancelAndIgnoreRemainingEvents()
            stateScope.cancel()
        }
    }

    @Test
    fun testDelete() = runTest {
        turbineScope {
            val individualRepository = mockIndividualRepository()
            val useCase = GetIndividualUiStateUseCase(individualRepository, mockk(relaxed = true))
            val stateScope = CoroutineScope(Job())
            val lastNavigationActionFlow = MutableStateFlow<NavigationAction?>(null)
            val navigationActionTurbine = lastNavigationActionFlow.testIn(stateScope)
            navigationActionTurbine.awaitItem() // consume default value

            val selectedIndividualId = TestIndividuals.individualId1
            val uiState = useCase(
                individualId = selectedIndividualId,
                coroutineScope = stateScope,
                navigate = { lastNavigationActionFlow.value = it }
            )

            val dialogUiStateFlowTurbine = uiState.dialogUiStateFlow.testIn(stateScope)
            dialogUiStateFlowTurbine.awaitItem() // consume default value

            uiState.onDeleteClick()
            val deleteDialog = dialogUiStateFlowTurbine.awaitItem()
            if (deleteDialog is MessageDialogUiState) {
                // test cancel pressed (no delete)
                deleteDialog.onDismiss?.invoke()
                coVerify(exactly = 0) { individualRepository.deleteIndividual(any()) }

                // test OK pressed (delete)
                deleteDialog.onConfirm?.invoke(Unit)
                coVerify(exactly = 1) { individualRepository.deleteIndividual(any()) }
            } else {
                error("Expected error Delete MessageDialogUiState")
            }

            navigationActionTurbine.cancelAndIgnoreRemainingEvents()
            dialogUiStateFlowTurbine.cancelAndIgnoreRemainingEvents()
            stateScope.cancel()
        }
    }
}