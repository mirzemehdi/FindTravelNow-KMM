package presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TitleDescription(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier,
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Start,
        )

        Text(
            modifier = Modifier,
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            textAlign = TextAlign.Start,
        )
    }

}