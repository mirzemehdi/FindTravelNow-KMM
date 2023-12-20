package presentation.screens.more

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import presentation.components.BoxListItem
import presentation.theme.strings.Strings
import util.AppOpenerUtil
import util.auth.google.GoogleButtonUiContainer
import util.logging.AppLogger

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    onNavigateAboutUs: () -> Unit,
    onNavigatePrivacyPolicy: () -> Unit,
    onNavigateTermsConditions: () -> Unit,
    onNavigateProfile: () -> Unit,
) {
    val moreScreenUiState by remember { mutableStateOf(MoreScreenUiState()) }
    val appOpenerUtil = koinInject<AppOpenerUtil>()
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
                    when (item) {
                        Strings.about_us -> onNavigateAboutUs()
                        Strings.rate_app -> appOpenerUtil.openStorePage()
                        Strings.privacy_policy -> onNavigatePrivacyPolicy()
                        Strings.terms_conditions -> onNavigateTermsConditions()
                        Strings.share_app -> appOpenerUtil.shareApp()
                        Strings.feedback -> appOpenerUtil.openFeedbackMail()
                        Strings.profile -> onNavigateProfile()
                        else -> Unit

                    }
                }
            )
        }
    }



}