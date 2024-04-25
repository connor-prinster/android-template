package inc.blubz.fitsync.ux.individual

import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.blubz.fitsync.R
import org.blubz.fitsync.analytics.Analytics
import org.blubz.fitsync.model.domain.inline.IndividualId
import org.blubz.fitsync.model.repository.IndividualRepository
import inc.blubz.fitsync.ui.compose.dialog.DialogUiState
import inc.blubz.fitsync.ui.compose.dialog.dismissDialog
import inc.blubz.fitsync.ui.compose.dialog.showMessageDialog
import inc.blubz.fitsync.ui.navigation.NavigationAction
import org.blubz.fitsync.util.ext.stateInDefault
import org.blubz.fitsync.ux.individualedit.IndividualEditRoute
import javax.inject.Inject

class GetIndividualUiStateUseCase
@Inject constructor(
    private val individualRepository: IndividualRepository,
    private val analytics: Analytics,
) {
    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    operator fun invoke(
        individualId: IndividualId,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit,
    ): IndividualUiState {
        return IndividualUiState(
            dialogUiStateFlow = dialogUiStateFlow,

            individualFlow = individualRepository.getIndividualFlow(individualId).stateInDefault(coroutineScope, null),

            onEditClick = { editIndividual(individualId, navigate) },
            onDeleteClick = { onDeleteClicked(individualId, coroutineScope, navigate) },
            deleteIndividual = { deleteIndividual(individualId, coroutineScope, navigate) },
        )
    }

    private fun onDeleteClicked(individualId: IndividualId, coroutineScope: CoroutineScope, navigate: (NavigationAction) -> Unit) {
        showMessageDialog(
            dialogUiStateFlow,
            text = { stringResource(R.string.delete_individual_confirm) },
            onConfirm = { deleteIndividual(individualId, coroutineScope, navigate) },
            onDismiss = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun deleteIndividual(individualId: IndividualId, coroutineScope: CoroutineScope, navigate: (NavigationAction) -> Unit) = coroutineScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
        navigate(NavigationAction.Pop())
    }

    private fun editIndividual(individualId: IndividualId, navigate: (NavigationAction) -> Unit) {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        navigate(NavigationAction.Navigate(IndividualEditRoute.createRoute(individualId)))
    }
}