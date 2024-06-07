package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.StateFlow


@Composable
expect fun <T> StateFlow<T>.asState(): State<T>

interface AppVersion {
    fun code(): String
    fun name(): String
}

expect fun isAndroid():Boolean

