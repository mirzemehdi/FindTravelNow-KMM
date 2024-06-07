package util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.core.content.pm.PackageInfoCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow


@Composable
actual fun <T> StateFlow<T>.asState(): State<T> = collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)


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