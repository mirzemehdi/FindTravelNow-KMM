import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.about.AboutScreen
import theme.MyApplicationTheme

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun App() {
    MyApplicationTheme {
//        OnBoardingScreen()
//        MoreScreen()
        AboutScreen()
    }
}



expect fun getPlatformName(): String