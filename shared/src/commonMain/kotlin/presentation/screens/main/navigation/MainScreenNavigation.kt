package presentation.screens.main.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import presentation.screens.home.HomeScreen
import presentation.screens.main.MainScreen
import presentation.screens.more.MoreScreen
import presentation.screens.top5flights.Top5FlightsScreen
import presentation.theme.strings.Strings

interface MainScreenDestination {


    object Home : Screen, MainScreenDestination {
        @Composable
        override fun Content() {
            HomeScreen()
        }
    }

    object More : Screen, MainScreenDestination {
        @Composable
        override fun Content() {
            MoreScreen()
        }

        override fun getTitle(): String = Strings.title_screen_more

    }

    object Top5Flights : Screen, MainScreenDestination {
        @Composable
        override fun Content() {
            Top5FlightsScreen()
        }

        override fun getTitle(): String = Strings.title_screen_top5_flights
    }

    fun getTitle(): String{
        return ""
    }

}

fun MainScreenDestination.isTopLevelScreen(): Boolean {
    val topLevelScreens = listOf(
        MainScreenDestination.Home,
        MainScreenDestination.More,
        MainScreenDestination.Top5Flights
    )
    return topLevelScreens.any { it::class == this::class }
}

@Composable
fun MainScreenNavigation() {
    val startScreen = MainScreenDestination.Home
    Navigator(screen = startScreen) { navigator ->
        val currentScreen = navigator.lastItem
        val currentDestination = currentScreen as MainScreenDestination
        MainScreen(
            currentDestination = currentDestination,
            onDestinationChanged = {
                val destinationScreen = it as Screen
                when {
                    it.isTopLevelScreen() && it::class == startScreen::class ->
                        navigator.replaceAll(destinationScreen)

                    it.isTopLevelScreen() -> with(navigator) {
                        popUntilRoot()
                        push(destinationScreen)
                    }

                    else -> navigator.push(destinationScreen)

                }
            },
            onNavigateBack = {
                navigator.pop()
            }
        ) {
//            CurrentScreen()
            AnimatedTransition(navigator)
        }
    }
}

@Composable
private fun AnimatedTransition(navigator: Navigator) {
    AnimatedContent(
        targetState = navigator.lastItem,
        transitionSpec = {

            val (initialScale, targetScale) = when (navigator.lastEvent) {
                StackEvent.Pop -> 1f to 0.85f
                else -> 0.85f to 1f
            }

            val stiffness = Spring.StiffnessMediumLow
            val enterTransition = fadeIn(tween(easing = EaseIn)) + scaleIn(
                spring(stiffness = stiffness),
                initialScale = initialScale
            )

            val exitTransition = fadeOut(spring(stiffness = stiffness)) + scaleOut(
                tween(easing = EaseOut),
                targetScale = targetScale
            )

            enterTransition togetherWith exitTransition
        }
    ) { currentScreen ->


        navigator.saveableState("transition", currentScreen) {
            currentScreen.Content()
        }

    }
}