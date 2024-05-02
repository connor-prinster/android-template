package inc.blubz.fitsync.ux.exercise

import inc.blubz.fitsync.model.db.main.exercise.WorkoutEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import inc.blubz.fitsync.ui.compose.dialog.DialogUiState

data class ExerciseUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val individualFlow: StateFlow<WorkoutEntity?> = MutableStateFlow(null),

    // Events
    val onEditClick: () -> Unit = {},
    val onDeleteClick: () -> Unit = {},
    val deleteIndividual: () -> Unit = {},
)