package inc.blubz.fitsync.ux.directory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import inc.blubz.fitsync.R
import inc.blubz.fitsync.ui.compose.appbar.AppBarMenu
import inc.blubz.fitsync.ui.compose.appbar.AppBarMenuItem
import inc.blubz.fitsync.ui.navigation.HandleNavigation
import inc.blubz.fitsync.ux.MainAppScaffoldWithNavBar

@Composable
fun DirectoryScreen(
    navController: NavController,
    viewModel: DirectoryViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        // icons
        AppBarMenuItem.Icon(Icons.Outlined.Search, R.string.search) {},

        // overflow
        AppBarMenuItem.OverflowMenuItem(R.string.settings) { uiState.onSettingsClicked() }
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.directory),
        navigationIconVisible = false,
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { uiState.onNewClicked() },
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add))
            }
        }
    ) {
        DirectoryContent(
            uiState,
        )
    }

    HandleNavigation(viewModel, navController)
}

@Composable
private fun DirectoryContent(
    uiState: DirectoryUiState
) {
    val directoryList by uiState.directoryListFlow.collectAsStateWithLifecycle()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(directoryList) { individual ->
            ListItem(
                headlineContent = { Text(individual.getFullName()) },
                Modifier
                    .clickable { uiState.onIndividualClicked(individual.individualId) },
            )
        }
    }
}
