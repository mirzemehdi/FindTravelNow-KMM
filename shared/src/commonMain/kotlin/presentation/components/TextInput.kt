package presentation.components

import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MyAppTextInput(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
) {


    val textColor = Color.Black
    val cursorColor = Color.Black
    val textStyle = MaterialTheme.typography.bodySmall
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    val backgroundColor=if (enabled) Color.White else Color.LightGray.copy(alpha = 0.4f)
    val shape = RoundedCornerShape(8.dp)
    val customTextSelectionColors = TextSelectionColors(
        handleColor = cursorColor,
        backgroundColor = MaterialTheme.colorScheme.primary
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFE3E2E2),
                    shape = shape
                )
                .background(
                    color = backgroundColor, shape = shape
                ).padding(16.dp),
            enabled = enabled,
            value = text,
            onValueChange = { onValueChange(it) },
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(cursorColor)
        )
    }


}


@Composable
fun MyAppLabelledTextInput(
    modifier: Modifier = Modifier,
    title: String,
    inputText: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier,
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(9.dp))

        MyAppTextInput(
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            text = inputText,
            onValueChange = onValueChange
        )
    }

}