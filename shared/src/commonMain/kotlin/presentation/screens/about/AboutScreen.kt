@file:OptIn(ExperimentalResourceApi::class)

package presentation.screens.about

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import findtravelnow_kmm.shared.generated.resources.Res
import findtravelnow_kmm.shared.generated.resources.ic_app_version
import findtravelnow_kmm.shared.generated.resources.ic_developer
import findtravelnow_kmm.shared.generated.resources.ic_email
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import presentation.components.ExpandableBoxItem
import presentation.components.TitleDescription
import presentation.theme.Orange_55
import presentation.theme.Red_48
import presentation.theme.strings.Strings
import util.AppVersion

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    var aboutScreenUiState by remember { mutableStateOf(AboutScreenUiState()) }
    val appVersion = koinInject<AppVersion>()

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
                    Strings.app_details -> AppDetailsExpandedContent(appVersionName = appVersion.name())
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
            imageRes = Res.drawable.ic_email,
            title = Strings.email_address_title,
            description = Strings.contact_email_address,
        )
    }
}

@Composable
private fun AppDetailsExpandedContent(appVersionName:String) {
    Column {
        ImageTitleDescription(
            imageRes = Res.drawable.ic_developer,
            title = Strings.developer_title,
            description = Strings.developer_description,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ImageTitleDescription(
            imageRes = Res.drawable.ic_app_version,
            title = Strings.app_version_title,
            description = appVersionName,
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ImageTitleDescription(
    modifier: Modifier = Modifier,
    imageRes: DrawableResource,
    title: String,
    description: String,
) {
    Row(modifier = modifier) {
        val gradientBackground = Brush.radialGradient(
            0f to Red_48,
            1f to Orange_55
        )
        val imageShape = RoundedCornerShape(6.dp)
        Image(
            modifier = Modifier
                .padding(top=4.dp)
                .size(32.dp)
                .clip(imageShape)
                .background(gradientBackground, shape = imageShape)
                .padding(6.dp),
            painter = painterResource(imageRes),
            contentDescription = null,
        )
        TitleDescription(modifier = Modifier.padding(start = 14.dp),title=title, description=description)
    }
}