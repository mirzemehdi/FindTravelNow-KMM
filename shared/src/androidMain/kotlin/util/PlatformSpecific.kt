package util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.measify.findtravelnow.shared.BuildConfig
import kotlinx.coroutines.flow.StateFlow


@SuppressLint("DiscouragedApi")
@Composable
actual fun font(res: String, weight: FontWeight, style: FontStyle): Font {
    val context = LocalContext.current
    println("MyPackageName: ${context.packageName}")
    val id = context.resources.getIdentifier(res, "font", context.packageName)
    return Font(id, weight, style)
}

@Composable
actual fun <T> StateFlow<T>.asState(): State<T> = collectAsStateWithLifecycle()
