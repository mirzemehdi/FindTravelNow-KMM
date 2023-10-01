package presentation.more

import theme.strings.Strings


data class MoreScreenUiState(
    val items: List<String> = listOf(
        Strings.feedback,
        Strings.share_app,
        Strings.rate_app,
        Strings.about_us,
        Strings.privacy_policy,
    ),
)