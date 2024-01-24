package presentation.screens.main

import presentation.screens.main.navigation.MainScreenDestination


enum class BottomNavItem(val iconRes: String) {
    HOME(iconRes = "drawable/ic_bottom_nav_home.xml"),
    Top5Flights(iconRes = "drawable/ic_bottom_nav_top5flights.xml"),
    Profile(iconRes = "drawable/ic_bottom_nav_profile.xml"),
    More(iconRes = "drawable/ic_bottom_nav_more.xml");


    fun asTopLevelDestination(): MainScreenDestination {
        return when (this) {
            HOME -> MainScreenDestination.Home
            Top5Flights -> MainScreenDestination.Top5Flights
            Profile -> MainScreenDestination.Profile
            More -> MainScreenDestination.More
        }
    }
}

fun MainScreenDestination.asBottomNavItem(): BottomNavItem {
    return when (this) {
        MainScreenDestination.Home -> BottomNavItem.HOME
        MainScreenDestination.More, MainScreenDestination.AboutUs -> BottomNavItem.More
        MainScreenDestination.Top5Flights -> BottomNavItem.Top5Flights
        MainScreenDestination.Profile -> BottomNavItem.Profile
        else -> BottomNavItem.HOME
    }
}

