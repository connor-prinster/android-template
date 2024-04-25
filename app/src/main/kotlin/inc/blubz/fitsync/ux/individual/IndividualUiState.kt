package inc.blubz.fitsync.ux.individual

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.blubz.fitsync.model.domain.Individual
import inc.blubz.fitsync.ui.compose.dialog.DialogUiState

data class IndividualUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val individualFlow: StateFlow<Individual?> = MutableStateFlow(null),

    // Events
    val onEditClick: () -> Unit = {},
    val onDeleteClick: () -> Unit = {},
    val deleteIndividual: () -> Unit = {},
)