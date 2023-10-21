package root

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
fun RootApp(modifier: Modifier=Modifier) {

    MyApplicationTheme {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            var rootAppUiState by remember { mutableStateOf(RootAppUiState()) }
            val userPreferences: UserPreferences = koinInject()
            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                val isOnBoardShown =
                    userPreferences.getBoolean(UserPreferences.KEY_IS_ONBOARD_SHOWN)
                rootAppUiState = RootAppUiState(isLoading = false, isOnBoardShown = isOnBoardShown)
            }

            when (rootAppUiState.isLoading) {
                true -> SplashScreen()
                false -> RootAppNavigation(isOnBoardShown = rootAppUiState.isOnBoardShown)
            }
        }

    }
}

@Composable
private fun RootAppNavigation(isOnBoardShown: Boolean) {
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