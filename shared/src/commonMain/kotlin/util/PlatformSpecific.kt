package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.flow.StateFlow


@Composable
expect fun <T> StateFlow<T>.asState(): State<T>
@Composable
expect fun font(res: String, weight: FontWeight, style: FontStyle): Font