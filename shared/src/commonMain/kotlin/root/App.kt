package root

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import data.source.preferences.UserPreferences
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import presentation.theme.MyApplicationTheme
import root.navigation.RootAppDestinations

@Composable
fun App() {
    var appUiState by remember { mutableStateOf(AppUiState()) }
    MyApplicationTheme {
        val userPreferences: UserPreferences = koinInject()
        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch {
            val isOnBoardShown = userPreferences.getBoolean(UserPreferences.KEY_IS_ONBOARD_SHOWN)
//            if (isOnBoardShown)  userPreferences.putBoolean(UserPreferences.KEY_IS_ONBOARD_SHOWN,false)
            appUiState = AppUiState(isLoading = false, isOnBoardShown = isOnBoardShown)
        }

        when (appUiState.isLoading) {
            true -> SplashScreen()
            false -> AppNavigation(isOnBoardShown = appUiState.isOnBoardShown)
        }
    }
}

@Composable
private fun AppNavigation(isOnBoardShown: Boolean) {
    val startScreen = if (isOnBoardShown) RootAppDestinations.Main else RootAppDestinations.OnBoarding
    Navigator(screen = startScreen)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        //TODO change with app logo
        Image(
            painter = painterResource("drawable/ic_horizontal_flight.xml"),
            contentDescription = null
        )
    }
}