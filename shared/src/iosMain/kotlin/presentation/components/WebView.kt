package presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun WebView(modifier: Modifier, url: String) {
    Box(modifier){
        Text("IosWebView-$url")
    }
}