package inc.blubz.fitsync.ux.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import inc.blubz.fitsync.ui.navigation.HandleNavBarNavigation
import inc.blubz.fitsync.util.ext.requireActivity
import inc.blubz.fitsync.ux.NavGraph

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(LocalContext.current.requireActivity()) // make sure we share the same ViewModel here and in MainAppScaffoldWithNavBar
) {
    val navController = rememberNavController()

    NavGraph(navController)

    HandleNavBarNavigation(viewModel, navController)
}
