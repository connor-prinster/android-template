package inc.blubz.fitsync.ux.exercise

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import inc.blubz.fitsync.ui.navigation.ViewModelNav
import inc.blubz.fitsync.ui.navigation.ViewModelNavImpl
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel
@Inject constructor(
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val args = ExerciseRoute.Arg
    val uiState: ExerciseUiState = ExerciseUiState()
}
