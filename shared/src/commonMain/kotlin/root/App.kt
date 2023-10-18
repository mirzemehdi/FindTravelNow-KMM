package root

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.source.preferences.UserPreferences
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import presentation.theme.MyApplicationTheme
import root.navigation.RootAppDestination
import root.navigation.RootAppNavigation

@Composable
fun App() {

    MyApplicationTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        ) {
            var appUiState by remember { mutableStateOf(AppUiState()) }
            val userPreferences: UserPreferences = koinInject()
            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                val isOnBoardShown =
                    userPreferences.getBoolean(UserPreferences.KEY_IS_ONBOARD_SHOWN)
                appUiState = AppUiState(isLoading = false, isOnBoardShown = isOnBoardShown)
            }

            when (appUiState.isLoading) {
                true -> SplashScreen()
                false -> AppNavigation(isOnBoardShown = appUiState.isOnBoardShown)
            }
        }

    }
}

@Composable
private fun AppNavigation(isOnBoardShown: Boolean) {
    val startDestination =
        if (isOnBoardShown) RootAppDestination.Main else RootAppDestination.OnBoarding
    RootAppNavigation(startDestination = startDestination)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource("drawable/ic_logo.xml"),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )
    }
}