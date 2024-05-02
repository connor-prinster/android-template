package inc.blubz.fitsync.ux.exercise

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import inc.blubz.fitsync.model.domain.inline.ExerciseId
import inc.blubz.fitsync.ui.navigation.NavComposeRoute
import inc.blubz.fitsync.ui.navigation.NavRoute
import inc.blubz.fitsync.ui.navigation.NavRouteDefinition
import inc.blubz.fitsync.ui.navigation.RouteUtil
import inc.blubz.fitsync.ui.navigation.asNavRoute
import inc.blubz.fitsync.ui.navigation.asNavRouteDefinition
import inc.blubz.fitsync.ux.NavIntentFilterPart

object ExerciseRoute : NavComposeRoute() {
    private const val ROUTE_BASE = "exercise"
    override val routeDefinition: NavRouteDefinition = "$ROUTE_BASE/${RouteUtil.defineArg(Arg.EXERCISE_ID)}".asNavRouteDefinition() // individual/{exerciseId}

    fun createRoute(exerciseId: ExerciseId): NavRoute {
        return "$ROUTE_BASE/${exerciseId.value}".asNavRoute() // individual/123456
    }

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(Arg.EXERCISE_ID) {
                type = NavType.StringType
            }
        )
    }

    // adb shell am start -W -a android.intent.action.VIEW -d "android-fitsync://individual/xxxxxx"
    override fun getDeepLinks(): List<NavDeepLink> {
        return listOf(
            navDeepLink { uriPattern = "${NavIntentFilterPart.DEFAULT_APP_SCHEME}://$ROUTE_BASE/${RouteUtil.defineArg(Arg.EXERCISE_ID)}" }, // android-fitsync://individual/{exerciseId},
            // navDeepLink { uriPattern = "${NavIntentFilterPart.DEFAULT_WEB_SCHEME_HOST}/$ROUTE_BASE/${RouteUtil.defineArg(Arg.INDIVIDUAL_ID)}" } // http://www.mysite.com/individual/{exerciseId}
        )
    }

    object Arg {
        const val EXERCISE_ID = "exerciseId"
    }
}