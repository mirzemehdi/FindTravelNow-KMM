package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import presentation.theme.Orange
import presentation.theme.Orange_51

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(8.dp)
    Button(
        modifier = modifier
            .height(64.dp)
            .clip(shape)
            .background(
                brush = Brush.linearGradient(
                    0f to Orange,
                    1f to Orange_51
                )
            ),
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = { onClick() },
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.titleSmall
        )
    }

}