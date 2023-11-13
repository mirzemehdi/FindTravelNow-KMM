import android.app.Activity
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import root.RootApp



@Composable
fun MainView() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.run {
//                val systemBarColor = Color(0xFFFFD700).toArgb()
//                statusBarColor = systemBarColor
//                WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars = true
            }
        }
    }


    RootApp()
}
