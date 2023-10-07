import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import root.App


@Composable
fun MainView() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.run {
                val systemBarColor = Color.White.toArgb()
                statusBarColor = systemBarColor
                WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars = true
            }
        }
    }
    App()
}
