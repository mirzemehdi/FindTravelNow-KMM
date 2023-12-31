package presentation.screens.about

import presentation.theme.strings.Strings


data class AboutScreenUiState(
    val items: List<String> = listOf(
        Strings.about_findtravelnow,
        Strings.contact_details,
        Strings.app_details,
    ),
    val expandedItem: String? = null,
)