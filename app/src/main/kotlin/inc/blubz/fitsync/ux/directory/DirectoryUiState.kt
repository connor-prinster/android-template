package inc.blubz.fitsync.ux.directory

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import inc.blubz.fitsync.model.db.main.directoryitem.DirectoryItemEntityView
import inc.blubz.fitsync.model.domain.inline.IndividualId

data class DirectoryUiState(
    // Data
    val directoryListFlow: StateFlow<List<DirectoryItemEntityView>> = MutableStateFlow(emptyList()),

    // Events
    val onNewClicked: () -> Unit = {},
    val onIndividualClicked: (individualId: IndividualId) -> Unit = {},
    val onSettingsClicked: () -> Unit = {}
)