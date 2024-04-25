package inc.blubz.fitsync.ux.directory

import kotlinx.coroutines.CoroutineScope
import org.blubz.fitsync.model.repository.IndividualRepository
import inc.blubz.fitsync.ui.navigation.NavigationAction
import org.blubz.fitsync.util.ext.stateInDefault
import org.blubz.fitsync.ux.individual.IndividualRoute
import org.blubz.fitsync.ux.individualedit.IndividualEditRoute
import org.blubz.fitsync.ux.settings.SettingsRoute
import javax.inject.Inject

class GetDirectoryUiStateUseCase
@Inject constructor(
    private val individualRepository: IndividualRepository,
) {
    operator fun invoke(
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit,
    ): DirectoryUiState {
        return DirectoryUiState(
            directoryListFlow = individualRepository.getDirectoryListFlow().stateInDefault(coroutineScope, emptyList()),
            onNewClicked = { navigate(NavigationAction.Navigate(IndividualEditRoute.createRoute())) },
            onIndividualClicked = { individualId -> navigate(NavigationAction.Navigate(IndividualRoute.createRoute(individualId))) },
            onSettingsClicked = { navigate(NavigationAction.Navigate(SettingsRoute.createRoute())) }
        )
    }
}