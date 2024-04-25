package inc.blubz.fitsync.ux.individualedit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.blubz.fitsync.model.domain.inline.IndividualId
import inc.blubz.fitsync.ui.navigation.NavComposeRoute
import inc.blubz.fitsync.ui.navigation.NavRoute
import inc.blubz.fitsync.ui.navigation.NavRouteDefinition
import inc.blubz.fitsync.ui.navigation.RouteUtil
import inc.blubz.fitsync.ui.navigation.asNavRoute
import inc.blubz.fitsync.ui.navigation.asNavRouteDefinition
import org.blubz.fitsync.util.ext.getIndividualId

object IndividualEditRoute : NavComposeRoute() {
    private const val ROUTE_BASE = "individualEdit"
    override val routeDefinition: NavRouteDefinition = "$ROUTE_BASE?${RouteUtil.defineOptionalArgs(Arg.INDIVIDUAL_ID)}".asNavRouteDefinition()

    fun createRoute(individualId: IndividualId? = null): NavRoute {
        return "$ROUTE_BASE?${RouteUtil.optionalArgs(mapOf(Arg.INDIVIDUAL_ID to individualId?.value))}".asNavRoute()
    }

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(Arg.INDIVIDUAL_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    }

    object Arg {
        const val INDIVIDUAL_ID = "individualId"
    }
}

data class IndividualEditArgs(val individualId: IndividualId?) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                savedStateHandle.getIndividualId(IndividualEditRoute.Arg.INDIVIDUAL_ID),
            )
}