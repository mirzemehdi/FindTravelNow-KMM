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
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.screens.about.AboutScreen
import presentation.screens.auth.signin.SignInScreen
import presentation.screens.home.HomeScreen
import presentation.screens.home.HomeUiStateHolder
import presentation.screens.main.MainScreen
import presentation.screens.main.MainUiStateHolder
import presentation.screens.more.MoreScreen
import presentation.screens.top5flights.Top5FlightsScreen
import presentation.screens.top5flights.Top5FlightsUiStateHolder
import presentation.screens.webview.WebViewScreen
import presentation.theme.strings.Strings
import util.asState
import util.getUiStateHolder

interface TopLevelScreenDestination : MainScreenDestination {
    companion object {
        fun getStartScreen(): TopLevelScreenDestination = MainScreenDestination.Profile
    }

}

interface MainScreenDestination {


    object Home : Screen, TopLevelScreenDestination {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            val uiStateHolder = getUiStateHolder<HomeUiStateHolder>()
            HomeScreen(
                uiStateHolder = uiStateHolder,
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

                },
                onNavigateFlightInfo = { flightInfo ->
                    navigator.navigate(
                        WebView(
                            title = Strings.category_flight,
                            url = flightInfo.getUrl()
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
                },
                onNavigateTermsConditions = {
                    navigator.navigate(
                        WebView(
                            url = Strings.url_terms_conditions,
                            title = Strings.terms_conditions
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
            val navigator = LocalNavigator.currentOrThrow
            val uiStateHolder = getUiStateHolder<Top5FlightsUiStateHolder>()
            Top5FlightsScreen(uiStateHolder = uiStateHolder, onNavigateFlightInfo = {
                navigator.navigate(
                    WebView(
                        title = Strings.category_flight,
                        url = it.getUrl()
                    )
                )
            })
        }

        override fun getTitle(): String = Strings.title_screen_top5_flights
    }

    object Profile : Screen, TopLevelScreenDestination {

        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
//            val uiStateHolder = getUiStateHolder<Top5FlightsUiStateHolder>()
            SignInScreen()
        }

        override fun getTitle(): String = Strings.title_screen_profile
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
fun MainScreenNavigation(uiStateHolder: MainUiStateHolder) {
    val startScreen = TopLevelScreenDestination.getStartScreen() as Screen
    Navigator(screen = startScreen) { navigator ->
        val currentScreen = navigator.lastItem
        val currentDestination = currentScreen as MainScreenDestination
        val uiState by uiStateHolder.uiState.asState()
        MainScreen(
            uiState = uiState,
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