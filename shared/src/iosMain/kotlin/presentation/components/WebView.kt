package presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKPreferences
import platform.WebKit.WKUIDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.WKWindowFeatures
import platform.WebKit.javaScriptEnabled
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebView(modifier: Modifier, url: String) {
    Box(modifier = modifier) {
        var isLoading by remember { mutableStateOf(true) }

        val uiDelegate = remember { WKUiDelegate() }
        val navigationDelegate = remember {
            WKNavigationDelegate {
                isLoading = it
            }
        }
        UIKitView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                val config = WKWebViewConfiguration().apply {
                    allowsInlineMediaPlayback = true
                    preferences = WKPreferences().apply {
                        javaScriptEnabled = true
                        javaScriptCanOpenWindowsAutomatically = true
                    }
                }
                WKWebView(
                    frame = CGRectZero.readValue(),
                    configuration = config
                ).apply {
                    userInteractionEnabled = true
                    allowsBackForwardNavigationGestures = true
                    this.setUIDelegate(uiDelegate)
                    this.navigationDelegate = navigationDelegate
                    loadRequest(request = NSURLRequest(NSURL(string = url)))
                }

            },
            onRelease = {
                it.navigationDelegate = null
                it.setUIDelegate(null)
            })

        if (isLoading)
            MyAppCircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
    }
}

/**
 * This solves new window or new tab opening in ios web view
 */
private class WKUiDelegate : WKUIDelegateProtocol, NSObject() {
    override fun webView(
        webView: WKWebView,
        createWebViewWithConfiguration: WKWebViewConfiguration,
        forNavigationAction: WKNavigationAction,
        windowFeatures: WKWindowFeatures,
    ): WKWebView? {
        val frame = forNavigationAction.targetFrame
        val isMainFrame = frame?.isMainFrame() ?: false
        if (!isMainFrame) webView.loadRequest(forNavigationAction.request)
        return null
    }
}

@Suppress("CONFLICTING_OVERLOADS")
class WKNavigationDelegate(private val onLoadingStateChanged: (Boolean) -> Unit = {}) : NSObject(),
    WKNavigationDelegateProtocol {

    //On start loading
    override fun webView(
        webView: WKWebView,
        didStartProvisionalNavigation: WKNavigation?,
    ) {
        onLoadingStateChanged(true)
    }

    /**
     * onRequest loaded
     */
    override fun webView(
        webView: WKWebView,
        didFinishNavigation: WKNavigation?,
    ) {
        onLoadingStateChanged(false)
    }

    /**
     * onRequestError
     */
    override fun webView(
        webView: WKWebView,
        didFailProvisionalNavigation: WKNavigation?,
        withError: NSError,
    ) {
        onLoadingStateChanged(false)
    }
}

