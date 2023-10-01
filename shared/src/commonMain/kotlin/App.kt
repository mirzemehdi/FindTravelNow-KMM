import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.screens.about.AboutScreen
import ui.theme.MyApplicationTheme

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun App() {
    MyApplicationTheme {
//        OnBoardingScreen()
//        MoreScreen()
        AboutScreen()
    }
}