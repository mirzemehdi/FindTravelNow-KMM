package ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.components.ExpandableBoxItem
import ui.theme.Black_alpha_8
import ui.theme.strings.Strings

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    var aboutScreenUiState by remember { mutableStateOf(AboutScreenUiState()) }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 30.dp, vertical = 35.dp)
    ) {
        items(aboutScreenUiState.items, key = { it }) { item ->
            ExpandableBoxItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 5.dp),
                text = item,
                isExpanded = aboutScreenUiState.expandedItem == item,
                onToggle = { aboutScreenUiState = aboutScreenUiState.copy(expandedItem = item) }
            ) {
                when (item) {
                    Strings.about_findtravelnow -> AboutFindTravelNowExpandedContent()
                    Strings.contact_details -> ContactDetailsExpandedContent()
                    Strings.app_details -> AppDetailsExpandedContent()
                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun AboutFindTravelNowExpandedContent() {
    Text(
        text = Strings.about_findtravelnow_text,
        color = Color.Black,
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
private fun ContactDetailsExpandedContent() {
    Column {
        ImageTitleDescription(
            imageRes = "drawable/ic_email.xml",
            title = Strings.email_address_title,
            description = Strings.email_address_description,
        )
    }
}

@Composable
private fun AppDetailsExpandedContent() {
    Column {
        ImageTitleDescription(
            imageRes = "drawable/ic_developer.xml",
            title = Strings.developer_title,
            description = Strings.developer_description,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ImageTitleDescription(
            imageRes = "drawable/ic_app_version.xml",
            title = Strings.app_version_title,
            description = Strings.app_version_description,
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ImageTitleDescription(
    modifier: Modifier = Modifier,
    imageRes: String,
    title: String,
    description: String,
) {
    Row(modifier = modifier) {
        val gradientBackground = Brush.radialGradient(
            0f to Color(0xFFDE3048),
            1f to Color(0xFFF9A455)
        )
        val imageShape = RoundedCornerShape(6.dp)
        Image(
            modifier = Modifier
                .padding(top=4.dp)
                .size(32.dp)
                .clip(imageShape)
                .shadow(
                    elevation = 19.dp,
                    spotColor = Black_alpha_8,
                    ambientColor = Black_alpha_8,
                    shape = imageShape
                )
                .background(gradientBackground, shape = imageShape)
                .padding(6.dp),
            painter = painterResource(imageRes),
            contentDescription = null,
        )
        Column(modifier = Modifier.padding(start = 14.dp)) {
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
}