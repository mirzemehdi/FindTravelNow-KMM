package ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.theme.Black_22
import ui.theme.Black_alpha_8
import ui.theme.Gray
import ui.theme.Orange_alpha_5
import ui.theme.Orange_alpha_50


@Composable
fun ExpandableBoxItem(
    modifier: Modifier = Modifier,
    text: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(10.dp)
    val backgroundColor by animateColorAsState(if (isExpanded) Orange_alpha_5.compositeOver(Color.White) else Color.White)
    val borderColor = if (isExpanded) Orange_alpha_50 else Color(0xFFF1E9E9)
    val iconRotation by animateFloatAsState(if (isExpanded) 90f else 0f)

    Column(modifier = modifier
        .shadow(
            elevation = 19.dp,
            spotColor = Black_alpha_8,
            ambientColor = Black_alpha_8,
            shape = shape
        )
        .border(
            width = 1.dp,
            color = borderColor,
            shape = shape
        )
        .drawBehind {
            drawRect(backgroundColor)
        }
        .animateContentSize()
        .clip(shape)
        .clickable { }
    ) {
        if (isExpanded) {
            TextIconRow(
                modifier = Modifier
                    .clickable { onToggle() }
                    .padding(start = 22.dp, end = 19.dp, top = 19.dp, bottom = 14.dp),
                text = text,
                iconRotation = iconRotation
            )
            Divider(color = Gray, modifier = Modifier.padding(start = 22.dp, end = 19.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.padding(start = 22.dp, end = 19.dp, bottom = 19.dp)) {
                content()
            }

        } else {
            TextIconRow(
                modifier = Modifier
                    .clickable { onToggle() }
                    .padding(start = 22.dp, end = 19.dp, top = 19.dp, bottom = 19.dp),
                text = text,
                iconRotation = iconRotation
            )
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
            .clickable { onClick() }
            .padding(start = 22.dp, top = 19.dp, bottom = 19.dp, end = 19.dp),
        contentAlignment = Alignment.CenterStart

    ) {
        TextIconRow(text = text)
    }
}

@Composable
private fun TextIconRow(
    modifier: Modifier = Modifier,
    text: String,
    imageVector: ImageVector = Icons.Default.KeyboardArrowRight,
    iconRotation: Float = 0f,
) {
    Row(modifier = modifier) {
        Text(
            text = text,
            color = Black_22,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.weight(1f)
        )

        Icon(
            modifier = Modifier.rotate(iconRotation),
            imageVector = imageVector,
            tint = Black_22,
            contentDescription = null,
        )
    }
}

