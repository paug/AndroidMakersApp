package com.androidmakers.ui.common.navigation

import androidx.navigation3.runtime.NavKey

/**
 * Handles navigation events (forward and back) by updating the navigation state.
 */
class Navigator(val state: NavigationState) {

    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            // This is a top level route, just switch to it
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Stack for ${state.topLevelRoute} not found")
        val currentRoute = currentStack.last()

        // If we're at the base of the current route, go back to the start route stack.
        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }

    /**
     * Navigate to a session detail, replacing any existing session detail in the stack.
     * This ensures clicking a different session in list-detail mode swaps the detail pane
     * instead of stacking.
     */
    fun navigateToSessionDetail(sessionId: String) {
        val stack = state.backStacks[state.topLevelRoute] ?: return
        stack.removeAll { it is SessionDetailKey }
        stack.add(SessionDetailKey(sessionId))
    }

    /**
     * Navigate from a deep link: switch to the given tab and push the detail key onto that tab's
     * back stack.
     */
    fun navigateFromDeepLink(tabKey: NavKey, detailKey: NavKey) {
        state.topLevelRoute = tabKey
        state.backStacks[tabKey]?.add(detailKey)
    }
}
