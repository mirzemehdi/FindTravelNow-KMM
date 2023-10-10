package presentation.screens.webview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WebViewScreen(modifier: Modifier = Modifier, url: String) {
    Box(modifier.fillMaxSize()) {
        Text("WebViewUrl - $url")
    }
}