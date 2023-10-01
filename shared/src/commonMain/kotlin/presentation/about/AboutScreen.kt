package presentation.about

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.components.ExpandableBoxItem
import theme.strings.Strings

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
    Text("AboutFindTravelNow ExpandedContent")
}

@Composable
private fun ContactDetailsExpandedContent() {
    Text("ContactDetails ExpandedContent")
}

@Composable
private fun AppDetailsExpandedContent() {
    Text("App Details ExpandedContent")
}