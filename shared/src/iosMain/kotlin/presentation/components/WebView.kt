package presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebView(modifier: Modifier, url: String) {
    Box(modifier = modifier) {
        var isLoading by remember { mutableStateOf(false) }
//        val state = rememberWebViewState(url)
//        state.webSettings.apply {
//            isJavaScriptEnabled = true
//        }
//        isLoading = state.isLoading
//        val list = state.errorsForCurrentRequest.toList()
//        println("WebViewErrors: $list")
//        com.multiplatform.webview.web.WebView(state)


//        webview.navigationDelegate = WKNavigationDelegate {
//            isLoading = it
//        }


        UIKitView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                val config = WKWebViewConfiguration().apply {
                    allowsInlineMediaPlayback = true
                    preferences.javaScriptCanOpenWindowsAutomatically = true
                    preferences.javaScriptEnabled = true

                }
                println("NewWebViewCreation")
                WKWebView(
                    frame = CGRectZero.readValue(),
                    configuration = config
                )
                    .also {
                        it.loadRequest(request = NSURLRequest(NSURL(string = url)))
                    }

            })

        if (isLoading)
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary
            )
    }
}

//private class WKNavigationDelegate(private val onLoadingStateChanged: (Boolean) -> Unit) :
//    NSObject(),
//    WKNavigationDelegateProtocol {
//
//    override fun webView(webView: WKWebView, didStartProvisionalNavigation: WKNavigation?) {
//        // Called when the web view starts loading a new page.
//        super.webView(webView, didStartProvisionalNavigation)
//        onLoadingStateChanged(true)
//    }
//
////    override fun webView(
////        webView: WKWebView,
////        didFinishNavigation: WKNavigation?,
////    ) {
////        // Called when the web view has successfully finished loading a page.
////        onLoadingStateChanged(false)
////
////
////    }
//
//
//    override fun webView(
//        webView: WKWebView,
//        didFailProvisionalNavigation: WKNavigation?,
//        withError: NSError,
//    ) {
//        // Called when the web view encounters an error during loading.
//        onLoadingStateChanged(false)
//
//
//    }
//
//}