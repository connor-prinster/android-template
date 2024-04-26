package inc.blubz.fitsync.ux.individual

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import inc.blubz.fitsync.model.domain.inline.IndividualId
import inc.blubz.fitsync.ui.navigation.NavComposeRoute
import inc.blubz.fitsync.ui.navigation.NavRoute
import inc.blubz.fitsync.ui.navigation.NavRouteDefinition
import inc.blubz.fitsync.ui.navigation.RouteUtil
import inc.blubz.fitsync.ui.navigation.asNavRoute
import inc.blubz.fitsync.ui.navigation.asNavRouteDefinition
import inc.blubz.fitsync.util.ext.requireIndividualId
import inc.blubz.fitsync.ux.NavIntentFilterPart

object IndividualRoute : NavComposeRoute() {
    private const val ROUTE_BASE = "individual"
    override val routeDefinition: NavRouteDefinition = "$ROUTE_BASE/${RouteUtil.defineArg(Arg.INDIVIDUAL_ID)}".asNavRouteDefinition() // individual/{individualId}

    fun createRoute(individualId: IndividualId): NavRoute {
        return "$ROUTE_BASE/${individualId.value}".asNavRoute() // individual/123456
    }

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(Arg.INDIVIDUAL_ID) {
                type = NavType.StringType
            }
        )
    }

    // adb shell am start -W -a android.intent.action.VIEW -d "android-fitsync://individual/xxxxxx"
    override fun getDeepLinks(): List<NavDeepLink> {
        return listOf(
            navDeepLink { uriPattern = "${NavIntentFilterPart.DEFAULT_APP_SCHEME}://$ROUTE_BASE/${RouteUtil.defineArg(Arg.INDIVIDUAL_ID)}" }, // android-fitsync://individual/{individualId},
            // navDeepLink { uriPattern = "${NavIntentFilterPart.DEFAULT_WEB_SCHEME_HOST}/$ROUTE_BASE/${RouteUtil.defineArg(Arg.INDIVIDUAL_ID)}" } // http://www.mysite.com/individual/{individualId}
        )
    }

    object Arg {
        const val INDIVIDUAL_ID = "individualId"
    }
}

data class IndividualArgs(val individualId: IndividualId) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                savedStateHandle.requireIndividualId(IndividualRoute.Arg.INDIVIDUAL_ID),
            )
}
