package inc.blubz.fitsync.ux

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.dbtools.android.work.ux.monitor.WorkManagerStatusScreen
import inc.blubz.fitsync.ui.navigation.NavUriLogger
import inc.blubz.fitsync.ui.navigation.WorkManagerStatusRoute
import inc.blubz.fitsync.ux.about.AboutRoute
import inc.blubz.fitsync.ux.about.AboutScreen
import inc.blubz.fitsync.ux.about.typography.TypographyRoute
import inc.blubz.fitsync.ux.about.typography.TypographyScreen
import inc.blubz.fitsync.ux.acknowledgement.AcknowledgementScreen
import inc.blubz.fitsync.ux.acknowledgement.AcknowledgmentsRoute
import inc.blubz.fitsync.ux.directory.DirectoryRoute
import inc.blubz.fitsync.ux.directory.DirectoryScreen
import inc.blubz.fitsync.ux.individual.IndividualRoute
import inc.blubz.fitsync.ux.individual.IndividualScreen
import inc.blubz.fitsync.ux.individualedit.IndividualEditRoute
import inc.blubz.fitsync.ux.individualedit.IndividualEditScreen
import inc.blubz.fitsync.ux.settings.SettingsRoute
import inc.blubz.fitsync.ux.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Debug navigation
    navController.addOnDestinationChangedListener(NavUriLogger())

    NavHost(
        navController = navController,
        startDestination = DirectoryRoute.routeDefinition.value
    ) {
        DirectoryRoute.addNavigationRoute(this) { DirectoryScreen(navController) }
        IndividualRoute.addNavigationRoute(this) { IndividualScreen(navController) }
        IndividualEditRoute.addNavigationRoute(this) { IndividualEditScreen(navController) }

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
