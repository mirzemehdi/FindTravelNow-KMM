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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.screens.about.AboutScreen
import presentation.screens.home.HomeScreen
import presentation.screens.main.MainScreen
import presentation.screens.more.MoreScreen
import presentation.screens.top5flights.Top5FlightsScreen
import presentation.screens.top5flights.Top5FlightsStateHolder
import presentation.screens.webview.WebViewScreen
import presentation.theme.strings.Strings
import util.getUiStateHolder

interface TopLevelScreenDestination : MainScreenDestination {
    companion object {
        fun getStartScreen(): TopLevelScreenDestination = MainScreenDestination.Home
    }

}

interface MainScreenDestination {


    object Home : Screen, TopLevelScreenDestination {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            HomeScreen(
                onNavigateTop5Flights = {
                    navigator.navigate(Top5Flights)
                },
                onNavigateCategory = { categoryData ->
                    navigator.navigate(
                        WebView(
                            title = categoryData.title,
                            url = categoryData.url
                        )
                    )

                }

            )
        }
    }

    object More : Screen, TopLevelScreenDestination {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            MoreScreen(
                onNavigateAboutUs = { navigator.navigate(AboutUs) },
                onNavigatePrivacyPolicy = {
                    navigator.navigate(
                        WebView(
                            url = Strings.url_privacy_policy,
                            title = Strings.privacy_policy
                        )
                    )
                }
            )
        }

        override fun getTitle(): String = Strings.title_screen_more

    }

    object Top5Flights : Screen, TopLevelScreenDestination {
        @Composable
        override fun Content() {
            val uiStateHolder = getUiStateHolder<Top5FlightsStateHolder>()
            Top5FlightsScreen(uiStateHolder = uiStateHolder)
        }

        override fun getTitle(): String = Strings.title_screen_top5_flights
    }

    object AboutUs : Screen, MainScreenDestination {
        @Composable
        override fun Content() {
            AboutScreen()
        }

        override fun getTitle(): String = Strings.about_us
    }

    fun getTitle(): String {
        return ""
    }

    data class WebView(val url: String, private val title: String = "") : Screen,
        MainScreenDestination {
        @Composable
        override fun Content() {
            WebViewScreen(url = url)
        }

        override fun getTitle(): String {
            return title
        }


    }

}

fun MainScreenDestination.isTopLevelScreen() = this is TopLevelScreenDestination

fun Navigator.navigate(destination: MainScreenDestination) {
    val destinationScreen = destination as Screen
    when {
        destination.isTopLevelScreen() && destination == TopLevelScreenDestination.getStartScreen() ->
            this.replaceAll(destinationScreen)

        destination.isTopLevelScreen() -> with(this) {
            popUntilRoot()
            push(destinationScreen)
        }

        else -> this.push(destinationScreen)
    }
}

@Composable
fun MainScreenNavigation() {
    val startScreen = TopLevelScreenDestination.getStartScreen() as Screen
    Navigator(screen = startScreen) { navigator ->
        val currentScreen = navigator.lastItem
        val currentDestination = currentScreen as MainScreenDestination
        MainScreen(
            currentDestination = currentDestination,
            onDestinationChanged = {
                navigator.navigate(it)
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