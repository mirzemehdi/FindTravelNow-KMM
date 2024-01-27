package presentation.screens.main

import domain.model.User


data class MainScreenUiState(
    val isAppVersionUpgradeRequired: Boolean = false,
    val currentUser: User? = null
)