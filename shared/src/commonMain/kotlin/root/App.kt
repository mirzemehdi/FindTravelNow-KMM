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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import root.navigation.Screens
import presentation.theme.MyApplicationTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {

    MyApplicationTheme {
        val userPreferences: UserPreferences = koinInject()
        var isLoading: Boolean by remember { mutableStateOf(true) }
        var isOnBoardShown by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch {
            val value = userPreferences.getBoolean(UserPreferences.KEY_IS_ONBOARD_SHOWN)
            isOnBoardShown = value
            isLoading = false
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                //TODO change with app logo
                Image(
                    painter = painterResource("drawable/ic_horizontal_flight.xml"),
                    contentDescription = null
                )
            }
        } else {
            val startScreen = if (isOnBoardShown) Screens.Home else Screens.OnBoarding
            Navigator(screen = startScreen)
        }

//        OnBoardingScreen()
//        MoreScreen()
//        AboutScreen()
//        HomeScreen()
//        Top5FlightsScreen()


    }
}