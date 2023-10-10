package presentation.screens.more

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.components.BoxListItem
import presentation.theme.strings.Strings

@Composable
fun MoreScreen(modifier: Modifier = Modifier, onNavigateAboutUs: () -> Unit) {
    val moreScreenUiState by remember { mutableStateOf(MoreScreenUiState()) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 30.dp, vertical = 35.dp)
    ) {
        items(moreScreenUiState.items, key = { it }) { item ->
            BoxListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 5.dp),
                text = item,
                onClick = {
                    val a = when (item) {
                        Strings.about_us -> onNavigateAboutUs()
                        else -> Unit

                    }
                }
            )
        }
    }

}