package presentation.screens.main

import presentation.screens.main.navigation.MainScreenDestination


enum class BottomNavItem(val iconRes: String) {
    HOME(iconRes = "drawable/ic_bottom_nav_home.xml"),
    Top5Flights(iconRes = "drawable/ic_bottom_nav_top5flights.xml"),
    More(iconRes = "drawable/ic_bottom_nav_more.xml");


    fun asTopLevelDestination(): MainScreenDestination {
        return when (this) {
            HOME -> MainScreenDestination.Home
            Top5Flights -> MainScreenDestination.Top5Flights
            More -> MainScreenDestination.More
        }
    }
}

fun MainScreenDestination.asBottomNavItem(): BottomNavItem {
    return when (this) {
        MainScreenDestination.Home -> BottomNavItem.HOME
        MainScreenDestination.More -> BottomNavItem.More
        MainScreenDestination.Top5Flights -> BottomNavItem.Top5Flights
        else -> BottomNavItem.HOME
    }
}

