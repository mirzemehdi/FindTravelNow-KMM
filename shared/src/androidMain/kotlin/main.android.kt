import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import root.RootApp


@Composable
fun MainView() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
//            (view.context as Activity).window.run {
////                val systemBarColor = Color.White.toArgb()
////                statusBarColor = systemBarColor
//                WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars = true
//            }
        }
    }


    RootApp()
}
