package inc.blubz.fitsync.ux.individual

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import inc.blubz.fitsync.ui.navigation.ViewModelNav
import inc.blubz.fitsync.ui.navigation.ViewModelNavImpl
import javax.inject.Inject

@HiltViewModel
class IndividualViewModel
@Inject constructor(
    getIndividualUiStateUseCase: GetIndividualUiStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val args = IndividualArgs(savedStateHandle)
    val uiState: IndividualUiState = getIndividualUiStateUseCase.invoke(args.individualId, viewModelScope) { navigate(it) }
}
