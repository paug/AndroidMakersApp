package com.androidmakers.ui.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator

/**
 * Create a navigation state that persists across recompositions.
 *
 * @param startRoute The top level route to start on. This should also be in [topLevelRoutes].
 * @param topLevelRoutes The top level routes in the app.
 */
@Composable
fun rememberNavigationState(
    startRoute: NavKey,
    topLevelRoutes: Set<NavKey>
): NavigationState {
    val topLevelRoute = remember { mutableStateOf(startRoute) }
    val backStacks = topLevelRoutes.associateWith { key ->
        rememberNavBackStack(navKeySavedStateConfig, key)
    }

    return remember(startRoute, topLevelRoutes) {
        NavigationState(
            startRoute = startRoute,
            topLevelRoute = topLevelRoute,
            backStacks = backStacks
        )
    }
}

/**
 * State holder for navigation state. This class does not modify its own state. It is designed
 * to be modified using the [Navigator] class.
 *
 * @param startRoute The start route. The user will exit the app through this route.
 * @param topLevelRoute The state object that backs the top level route.
 * @param backStacks The back stacks for each top level route.
 */
class NavigationState(
    val startRoute: NavKey,
    topLevelRoute: MutableState<NavKey>,
    val backStacks: Map<NavKey, NavBackStack<NavKey>>
) {

    /**
     * The currently selected top level route.
     */
    var topLevelRoute: NavKey by topLevelRoute

    /**
     * Convert the navigation state into [NavEntry]s that have been decorated with a
     * SaveableStateHolder.
     *
     * @param entryProvider The entry provider used to convert the keys in the back stacks
     * to [NavEntry]s.
     */
    @Composable
    fun toDecoratedEntries(
        entryProvider: (NavKey) -> NavEntry<NavKey>
    ): List<NavEntry<NavKey>> {
        val decoratedEntries = backStacks.mapValues { (_, stack) ->
            val decorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
            )
            rememberDecoratedNavEntries(
                backStack = stack,
                entryDecorators = decorators,
                entryProvider = entryProvider
            )
        }

        return getTopLevelRoutesInUse()
            .flatMap { decoratedEntries[it] ?: emptyList() }
    }

    /**
     * Get the top level routes that are currently in use. The start route is always the first
     * route in the list ("exit through home" pattern).
     */
    private fun getTopLevelRoutesInUse(): List<NavKey> =
        if (topLevelRoute == startRoute) {
            listOf(startRoute)
        } else {
            listOf(startRoute, topLevelRoute)
        }
}
