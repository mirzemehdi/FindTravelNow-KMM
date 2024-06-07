package presentation.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.components.AppUpgradeRequiredDialog
import presentation.components.MyAppToolbar
import presentation.screens.main.navigation.MainScreenDestination
import presentation.screens.main.navigation.MainScreenNavigation
import presentation.screens.main.navigation.isTopLevelScreen
import presentation.theme.Silver_d8


@Composable
fun MainScreen(uiStateHolder: MainUiStateHolder) {
    MainScreenNavigation(uiStateHolder)
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    uiState: MainScreenUiState,
    currentDestination: MainScreenDestination,
    onDestinationChanged: (MainScreenDestination) -> Unit,
    onNavigateBack: () -> Unit,
    content: @Composable () -> Unit,
) {

    if (uiState.isAppVersionUpgradeRequired) {
        AppUpgradeRequiredDialog()
    }


    Column(modifier = modifier.fillMaxSize()) {
        val isToolbarInvisible = (currentDestination == MainScreenDestination.Home)
                || (currentDestination == MainScreenDestination.Account && uiState.currentUser == null)

        AnimatedVisibility(isToolbarInvisible.not()) {
            MyAppToolbar(
                title = if (isToolbarInvisible) "" else currentDestination.getTitle(),
                onNavigationIconClick = { onNavigateBack() }
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            content()
        }

        if (currentDestination.isTopLevelScreen()) {
            BottomNavigation(
                selectedNavItem = currentDestination.asBottomNavItem(),
                onNavigationItemSelected = {
                    onDestinationChanged(it.asTopLevelDestination())
                })
        }

    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BottomNavigation(
    modifier: Modifier = Modifier,
    selectedNavItem: BottomNavItem,
    onNavigationItemSelected: (BottomNavItem) -> Unit,
) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) {
        val navItems = BottomNavItem.values()
        navItems.forEach { item ->
            val isSelected = item == selectedNavItem
            val iconTint = if (isSelected) MaterialTheme.colorScheme.secondary else Silver_d8
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.iconRes),
                        contentDescription = null,
                        tint = iconTint
                    )
                },
                selected = isSelected,
                alwaysShowLabel = false,

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = MaterialTheme.colorScheme.background,
                    unselectedIconColor = Silver_d8,
                ),
                onClick = { onNavigationItemSelected(item) }
            )
        }
    }


}