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
    }

}

fun MainScreenDestination.isTopLevelScreen(): Boolean {
    val topLevelScreens = listOf(MainScreenDestination.Home, MainScreenDestination.More)
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
            }
        ) {
            AnimatedTransition(navigator)
        }
    }
}

@Composable
private fun AnimatedTransition(navigator: Navigator) {
    AnimatedContent(
        targetState = navigator.lastItem,
        transitionSpec = {

            val (initialOffset, targetOffset) = when (navigator.lastEvent) {
                StackEvent.Pop -> ({ size: Int -> -size }) to ({ size: Int -> size })
                else -> ({ size: Int -> size }) to ({ size: Int -> -size })
            }

            val (initialScale, targetScale) = when (navigator.lastEvent) {
                StackEvent.Pop -> 1f to 0.85f
                else -> 0.85f to 1f
            }

            val stiffness = Spring.StiffnessLow
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