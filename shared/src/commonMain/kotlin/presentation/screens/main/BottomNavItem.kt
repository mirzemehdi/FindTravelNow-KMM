@file:OptIn(ExperimentalResourceApi::class)

package presentation.screens.main

import findtravelnow_kmm.shared.generated.resources.Res
import findtravelnow_kmm.shared.generated.resources.ic_bottom_nav_home
import findtravelnow_kmm.shared.generated.resources.ic_bottom_nav_more
import findtravelnow_kmm.shared.generated.resources.ic_bottom_nav_profile
import findtravelnow_kmm.shared.generated.resources.ic_bottom_nav_top5flights
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.screens.main.navigation.MainScreenDestination


enum class BottomNavItem(val iconRes: DrawableResource) {
    HOME(iconRes = Res.drawable.ic_bottom_nav_home),
    Top5Flights(iconRes = Res.drawable.ic_bottom_nav_top5flights),
    Profile(iconRes = Res.drawable.ic_bottom_nav_profile),
    More(iconRes = Res.drawable.ic_bottom_nav_more);


    fun asTopLevelDestination(): MainScreenDestination {
        return when (this) {
            HOME -> MainScreenDestination.Home
            Top5Flights -> MainScreenDestination.Top5Flights
            Profile -> MainScreenDestination.Account
            More -> MainScreenDestination.More
        }
    }
}

fun MainScreenDestination.asBottomNavItem(): BottomNavItem {
    return when (this) {
        MainScreenDestination.Home -> BottomNavItem.HOME
        MainScreenDestination.More, MainScreenDestination.AboutUs -> BottomNavItem.More
        MainScreenDestination.Top5Flights -> BottomNavItem.Top5Flights
        MainScreenDestination.Account -> BottomNavItem.Profile
        else -> BottomNavItem.HOME
    }
}

