package inc.blubz.fitsync.ux.individualedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import inc.blubz.fitsync.ui.navigation.ViewModelNav
import inc.blubz.fitsync.ui.navigation.ViewModelNavImpl
import javax.inject.Inject

@HiltViewModel
class IndividualEditViewModel
@Inject constructor(
    getIndividualEditUiStateUseCase: GetIndividualEditUiStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val args = IndividualEditArgs(savedStateHandle)
    val uiState: IndividualEditUiState = getIndividualEditUiStateUseCase(args.individualId, viewModelScope) { navigate(it) }
}
