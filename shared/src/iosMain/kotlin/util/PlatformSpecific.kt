package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import platform.Foundation.NSBundle

private val cache: MutableMap<String, Font> = mutableMapOf()

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun font(res: String, weight: FontWeight, style: FontStyle): Font {
    return cache.getOrPut(res) {
        val byteArray = runBlocking {
            resource("font/$res.ttf").readBytes()
        }
        androidx.compose.ui.text.platform.Font(res, byteArray, weight, style)
    }
}

@Composable
actual fun <T> StateFlow<T>.asState(): State<T> = collectAsState()


internal class IosAppVersion : AppVersion {
    override fun code(): String =
        kotlin.runCatching { getInfoDictionary()?.get("CFBundleVersion") as? String ?: "" }
            .getOrDefault("")

    override fun name(): String =
        kotlin.runCatching {
            getInfoDictionary()?.get("CFBundleShortVersionString") as? String ?: ""
        }.getOrDefault("")


    private fun getInfoDictionary() = NSBundle.mainBundle.infoDictionary

}

actual fun isAndroid() = false