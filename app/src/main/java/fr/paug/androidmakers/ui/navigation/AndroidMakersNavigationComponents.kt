package fr.paug.androidmakers.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.AMUser
import fr.paug.androidmakers.ui.components.SigninButton
import fr.paug.androidmakers.util.AndroidMakersNavigationContentPosition

@Composable
fun AndroidMakersBottomNavigationBar(
    selectedDestination: NavDestination?,
    navigateToTopLevelDestination: (AndroidMakersTopLevelDestination) -> Unit
) {
  NavigationBar(
      modifier = Modifier.fillMaxWidth(),
      containerColor = MaterialTheme.colorScheme.background,
      contentColor = MaterialTheme.colorScheme.onBackground
  ) {
    TOP_LEVEL_DESTINATIONS.forEach { androidMakersDestination ->
      NavigationBarItem(
          selected = selectedDestination?.hierarchy?.any { destination ->
            destination.route == androidMakersDestination.route
          } == true,
          onClick = { navigateToTopLevelDestination(androidMakersDestination) },
          icon = {
            Icon(
                imageVector = androidMakersDestination.selectedIcon,
                contentDescription = stringResource(id = androidMakersDestination.iconTextId)
            )
          },
          label = { Text(stringResource(androidMakersDestination.iconTextId)) },
          colors = NavigationBarItemDefaults.colors(
              selectedIconColor = MaterialTheme.colorScheme.onSurface,
              selectedTextColor = MaterialTheme.colorScheme.onSurface,
              unselectedIconColor = MaterialTheme.colorScheme.onSurface,
              unselectedTextColor = MaterialTheme.colorScheme.onSurface,
              indicatorColor = MaterialTheme.colorScheme.surface
          )
      )
    }
  }
}
@Composable
fun AndroidMakersNavigationRail(
    selectedDestination: NavDestination?,
    navigationContentPosition: AndroidMakersNavigationContentPosition,
    navigateToTopLevelDestination: (AndroidMakersTopLevelDestination) -> Unit,
) {
  NavigationRail(
      modifier = Modifier.fillMaxHeight(),
      containerColor = MaterialTheme.colorScheme.background,
      contentColor = MaterialTheme.colorScheme.onBackground
  ) {
    // TODO remove custom nav rail positioning when NavRail component supports it. ticket : b/232495216
    Layout(
        modifier = Modifier.widthIn(max = 80.dp),
        content = {
          Column(
              modifier = Modifier.layoutId(LayoutType.HEADER),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
//            NavigationRailItem(
//                selected = false,
//                onClick = {  },
//                icon = {
//                  Icon(
//                      modifier = Modifier.size(28.dp),
//                      painter = painterResource(id = R.drawable.notification),
//                      contentDescription = "Logo"
//                  )
//                }
//            )
            Spacer(Modifier.height(8.dp)) // NavigationRailHeaderPadding
            Spacer(Modifier.height(4.dp)) // NavigationRailVerticalPadding
          }

          Column(
              modifier = Modifier.layoutId(LayoutType.CONTENT),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            TOP_LEVEL_DESTINATIONS.forEach { androidMakersDestination ->
              NavigationRailItem(
                  selected = selectedDestination?.hierarchy?.any { destination ->
                    destination.route == androidMakersDestination.route
                  } == true,
                  onClick = { navigateToTopLevelDestination(androidMakersDestination) },
                  icon = {
                    Icon(
                        imageVector = androidMakersDestination.selectedIcon,
                        contentDescription = stringResource(
                            id = androidMakersDestination.iconTextId
                        )
                    )
                  },
                  label = { Text(stringResource(androidMakersDestination.iconTextId)) },
                  colors = NavigationRailItemDefaults.colors(
                      selectedIconColor = MaterialTheme.colorScheme.onSurface,
                      selectedTextColor = MaterialTheme.colorScheme.onSurface,
                      unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                      unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                      indicatorColor = MaterialTheme.colorScheme.surface
                  )
              )
            }
          }
        },
        measurePolicy = { measurables, constraints ->
          lateinit var headerMeasurable: Measurable
          lateinit var contentMeasurable: Measurable
          measurables.forEach {
            when (it.layoutId) {
              LayoutType.HEADER -> headerMeasurable = it
              LayoutType.CONTENT -> contentMeasurable = it
              else -> error("Unknown layoutId encountered!")
            }
          }

          val headerPlaceable = headerMeasurable.measure(constraints)
          val contentPlaceable = contentMeasurable.measure(
              constraints.offset(vertical = -headerPlaceable.height)
          )
          layout(constraints.maxWidth, constraints.maxHeight) {
            // Place the header, this goes at the top
            headerPlaceable.placeRelative(0, 0)

            // Determine how much space is not taken up by the content
            val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

            val contentPlaceableY = when (navigationContentPosition) {
              // Figure out the place we want to place the content, with respect to the
              // parent (ignoring the header for now)
              AndroidMakersNavigationContentPosition.TOP -> 0
              AndroidMakersNavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
            }
                // And finally, make sure we don't overlap with the header.
                .coerceAtLeast(headerPlaceable.height)

            contentPlaceable.placeRelative(0, contentPlaceableY)
          }
        }
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidMakersTopAppBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    user: AMUser?
) {
  val navBackStackEntry by navHostController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  TopAppBar(
      modifier = modifier,
      scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
      colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.background,
          scrolledContainerColor = MaterialTheme.colorScheme.background,
          titleContentColor = MaterialTheme.colorScheme.onBackground,
          actionIconContentColor = MaterialTheme.colorScheme.onBackground,
      ),
      navigationIcon = {
        when (currentRoute) {
          AndroidMakersRoute.SPEAKER_DETAILS,
          AndroidMakersRoute.AGENDA_DETAILS -> {
            IconButton(onClick = { navHostController.navigateUp() }) {
              Icon(
                  imageVector = Icons.Filled.ArrowBack,
                  contentDescription = "Back"
              )
            }
          }
          else -> {
            Box(modifier = Modifier.padding(14.dp)) {
              Image(
                  modifier = Modifier.size(28.dp),
                  painter = painterResource(id = R.drawable.notification),
                  contentDescription = "Logo"
              )
            }
          }
        }
      },
      title = {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)) {
          Text(
              text = stringResource(R.string.app_name),
              style = MaterialTheme.typography.headlineSmall,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
          )
        }
      },
      actions = {
        when (currentRoute) {
          AndroidMakersRoute.AGENDA_LIST -> {
            IconButton(
                onClick = {

                }
            ) {
              Icon(
                  imageVector = Icons.Rounded.FilterList,
                  contentDescription = stringResource(R.string.filter),
              )
            }
          }
          AndroidMakersRoute.AGENDA_DETAILS -> {
            IconButton(
                onClick = {

                }
            ) {
              Icon(
                  imageVector = Icons.Rounded.Share,
                  contentDescription = stringResource(R.string.filter),
              )
            }
          }
          else -> { /* No-op */ }
        }
        if (currentRoute != AndroidMakersRoute.AGENDA_DETAILS &&
            currentRoute != AndroidMakersRoute.SPEAKER_DETAILS) {
          SigninButton(user)
        }
      }
  )
}

enum class LayoutType {
  HEADER, CONTENT
}