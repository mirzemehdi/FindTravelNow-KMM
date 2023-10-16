package root.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.screens.main.MainScreen
import presentation.screens.onboarding.OnBoardingScreen
import presentation.screens.onboarding.OnBoardingUiStateHolder
import util.getUiStateHolder

interface RootAppDestination {
    object OnBoarding : Screen, RootAppDestination {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            val onBoardingUiStateHolder = getUiStateHolder<OnBoardingUiStateHolder>()
            OnBoardingScreen(
                uiStateHolder = onBoardingUiStateHolder,
                onNavigateMain = { navigator.replace(Main) }
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