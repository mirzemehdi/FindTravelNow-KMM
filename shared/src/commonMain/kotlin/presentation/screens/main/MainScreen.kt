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
import presentation.components.MyAppToolbar
import presentation.screens.main.navigation.MainScreenDestination
import presentation.screens.main.navigation.MainScreenNavigation
import presentation.theme.Silver_d8


@Composable
fun MainScreen() {
    MainScreenNavigation()
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    currentDestination: MainScreenDestination,
    onDestinationChanged: (MainScreenDestination) -> Unit,
    onNavigateBack: () -> Unit,
    content: @Composable () -> Unit,
) {


    Column(modifier = modifier.fillMaxSize()) {

        AnimatedVisibility(currentDestination!=MainScreenDestination.Home){
            MyAppToolbar(
                title = currentDestination.getTitle(),
                onNavigationIconClick = { onNavigateBack() }
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            content()
        }

        BottomNavigation(
            selectedNavItem = currentDestination.asBottomNavItem(),
            onNavigationItemSelected = {
                onDestinationChanged(it.asTopLevelDestination())
            })


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
                        contentDescription = item.iconRes,
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