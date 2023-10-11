package presentation.screens.webview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.components.WebView

@Composable
fun WebViewScreen(modifier: Modifier = Modifier, url: String) {
    WebView(url = url, modifier = modifier.fillMaxSize())

}