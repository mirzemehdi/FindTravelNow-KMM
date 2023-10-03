import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.screens.about.AboutScreen
import ui.screens.home.HomeScreen
import ui.screens.top5flights.Top5FlightsScreen
import ui.theme.MyApplicationTheme

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun App() {
    MyApplicationTheme {
//        OnBoardingScreen()
//        MoreScreen()
//        AboutScreen()
        HomeScreen()
//        Top5FlightsScreen()
    }
}