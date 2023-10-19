package root.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.screens.main.MainScreen
import presentation.screens.main.navigation.MainScreenDestination
import presentation.screens.main.navigation.navigate
import presentation.screens.onboarding.OnBoardingScreen
import presentation.screens.onboarding.OnBoardingUiStateHolder
import presentation.theme.strings.Strings
import util.getUiStateHolder

interface RootAppDestination {
    object OnBoarding : Screen, RootAppDestination {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            val onBoardingUiStateHolder = getUiStateHolder<OnBoardingUiStateHolder>()
            OnBoardingScreen(
                uiStateHolder = onBoardingUiStateHolder,
                onNavigateMain = { navigator.replace(Main) },
                onClickPrivacyPolicy = {
                    navigator.navigate(
                        MainScreenDestination.WebView(
                            title = Strings.privacy_policy,
                            url = Strings.url_privacy_policy
                        )
                    )
                },
                onClickTermsService = {
                    navigator.navigate(
                        MainScreenDestination.WebView(
                            title = Strings.terms_conditions,
                            url = Strings.url_terms_conditions
                        )
                    )
                },
            )
        }
    }

    object Main : Screen, RootAppDestination {
        @Composable
        override fun Content() {
            MainScreen()
        }

    }
}

@Composable
fun RootAppNavigation(startDestination: RootAppDestination) {
    Navigator(screen = startDestination as Screen)
}