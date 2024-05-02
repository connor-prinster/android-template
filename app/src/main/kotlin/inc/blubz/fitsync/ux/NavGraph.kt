package inc.blubz.fitsync.ux

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import inc.blubz.fitsync.ui.navigation.NavUriLogger
import inc.blubz.fitsync.ui.navigation.WorkManagerStatusRoute
import inc.blubz.fitsync.ux.about.AboutRoute
import inc.blubz.fitsync.ux.about.AboutScreen
import inc.blubz.fitsync.ux.about.typography.TypographyRoute
import inc.blubz.fitsync.ux.about.typography.TypographyScreen
import inc.blubz.fitsync.ux.acknowledgement.AcknowledgementScreen
import inc.blubz.fitsync.ux.acknowledgement.AcknowledgmentsRoute
import inc.blubz.fitsync.ux.exercise.ExerciseRoute
import inc.blubz.fitsync.ux.exercise.IndividualScreen
import inc.blubz.fitsync.ux.home.HomeRoute
import inc.blubz.fitsync.ux.home.HomeScreen
import inc.blubz.fitsync.ux.settings.SettingsRoute
import inc.blubz.fitsync.ux.settings.SettingsScreen
import org.dbtools.android.work.ux.monitor.WorkManagerStatusScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Debug navigation
    navController.addOnDestinationChangedListener(NavUriLogger())

    NavHost(
        navController = navController,
        startDestination = HomeRoute.routeDefinition.value
    ) {
        ExerciseRoute.addNavigationRoute(this) { IndividualScreen(navController) }
        HomeRoute.addNavigationRoute(this) { HomeScreen(navController = navController) }

        SettingsRoute.addNavigationRoute(this) { SettingsScreen(navController) }
        AboutRoute.addNavigationRoute(this) { AboutScreen(navController) }

        TypographyRoute.addNavigationRoute(this) { TypographyScreen(navController) }

        AcknowledgmentsRoute.addNavigationRoute(this) { AcknowledgementScreen(navController) }

        WorkManagerStatusRoute.addNavigationRoute(this) { WorkManagerStatusScreen { navController.popBackStack() } }
    }
}

/**
 * Used for building Compose Navigation deeplinks.
 *
 * Provides common values to build navDeepLink uriPattern (used in AndroidManifest intent-filter)
 */
object NavIntentFilterPart {
    const val DEFAULT_APP_SCHEME = "android-fitsync"
    const val DEFAULT_WEB_SCHEME_HOST = "http://www.mysite.com"
}
