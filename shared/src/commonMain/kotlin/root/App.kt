package root

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import data.source.preferences.UserPreferences
import data.source.preferences.UserPreferencesImpl
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import ui.screens.about.AboutScreen
import ui.screens.home.HomeScreen
import ui.screens.onboarding.OnBoardingScreen
import ui.screens.top5flights.Top5FlightsScreen
import ui.theme.MyApplicationTheme

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun App() {
    MyApplicationTheme {
        val userPreferences:UserPreferences = koinInject()
//        val userPreferences :UserPreferences = UserPreferencesImpl()
        var isOnBoardShown by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            val value=userPreferences.getBoolean(UserPreferences.KEY_IS_ONBOARD_SHOWN)
            isOnBoardShown=value
        }
        if (isOnBoardShown) Top5FlightsScreen()
        else OnBoardingScreen()
//        OnBoardingScreen()
//        MoreScreen()
//        AboutScreen()
//        HomeScreen()
//        Top5FlightsScreen()
    }
}