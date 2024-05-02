package inc.blubz.fitsync.ux.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import inc.blubz.fitsync.R
import inc.blubz.fitsync.model.db.main.exercise.WorkoutEntity
import inc.blubz.fitsync.ui.compose.appbar.AppBarMenu
import inc.blubz.fitsync.ui.compose.appbar.AppBarMenuItem
import inc.blubz.fitsync.ui.compose.dialog.HandleDialogUiState
import inc.blubz.fitsync.ui.compose.form.TextWithTitle
import inc.blubz.fitsync.ui.compose.util.DateUiUtil
import inc.blubz.fitsync.ui.navigation.HandleNavigation
import inc.blubz.fitsync.ux.MainAppScaffoldWithNavBar
import kotlinx.datetime.LocalDate

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        AppBarMenuItem.Icon(Icons.Outlined.Edit, { stringResource(R.string.edit) }) { uiState.onEditClick() },
        AppBarMenuItem.Icon(Icons.Outlined.Delete, { stringResource(R.string.delete) }) { uiState.onDeleteClick() }
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.app_name),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() },
    ) {

    }

    HandleDialogUiState(uiState.dialogUiStateFlow)

    HandleNavigation(viewModel, navController)
}