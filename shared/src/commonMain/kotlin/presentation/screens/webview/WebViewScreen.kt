package presentation.screens.webview

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.components.WebView

@Composable
fun WebViewScreen(modifier: Modifier = Modifier, url: String) {
    val padding = WindowInsets.systemBars.asPaddingValues()
    WebView(url = url, modifier = modifier.fillMaxSize().padding(bottom = padding.calculateBottomPadding()))

}