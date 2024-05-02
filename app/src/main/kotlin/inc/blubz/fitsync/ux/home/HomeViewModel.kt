package inc.blubz.fitsync.ux.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import inc.blubz.fitsync.ui.navigation.ViewModelNav
import inc.blubz.fitsync.ui.navigation.ViewModelNavImpl
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    val uiState: HomeUiState = HomeUiState()

    private val navOptions: List<String> = listOf(
        "Workouts",
        "Exercises",
        "Tags",
    )
}
