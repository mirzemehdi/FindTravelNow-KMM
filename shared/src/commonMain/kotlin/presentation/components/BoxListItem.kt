package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import theme.Black_22
import theme.Black_alpha_8


@Composable
fun ExpandableBoxItem(modifier: Modifier = Modifier, isExpanded: Boolean = false, text: String) {
    Column(modifier = modifier) {

        if (isExpanded) {
            BoxListItem(
                modifier = modifier.fillMaxWidth(),
                text = text, onClick = {})
            Spacer(modifier = Modifier.height(100.dp))

        } else {
            BoxListItem(
                modifier = modifier.fillMaxWidth(),
                text = text, onClick = {})
        }

    }

}

@Composable
fun BoxListItem(
    modifier: Modifier = Modifier,
    text: String, onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier = modifier
            .shadow(
                elevation = 19.dp,
                spotColor = Black_alpha_8,
                ambientColor = Black_alpha_8
            )
            .border(
                width = 1.dp,
                color = Color(0xFFF1E9E9),
                shape = shape
            )
            .background(color = Color.White, shape = shape)
            .clip(shape)
            .clickable { }
            .padding(start = 22.dp, top = 19.dp, bottom = 19.dp, end = 19.dp),
        contentAlignment = Alignment.CenterStart

    ) {
        Text(
            text = text,
            color = Black_22,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            tint = Black_22,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}