package util

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.pm.PackageInfoCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow


@SuppressLint("DiscouragedApi")
@Composable
actual fun font(res: String, weight: FontWeight, style: FontStyle): Font {
    val context = LocalContext.current
    val id = context.resources.getIdentifier(res, "font", context.packageName)
    return Font(id, weight, style)
}

@Composable
actual fun <T> StateFlow<T>.asState(): State<T> = collectAsStateWithLifecycle()


internal class AndroidAppVersion(private val context: Context) : AppVersion {
    override fun code(): String =
        kotlin.runCatching { PackageInfoCompat.getLongVersionCode(getPackageInfo()).toString() }
            .getOrDefault("")

    override fun name(): String =
        kotlin.runCatching { getPackageInfo().versionName }.getOrDefault("")

    private fun getPackageInfo() =
        context.packageManager.getPackageInfo(context.packageName, 0)

}

actual fun isAndroid() = true