package presentation.screens.account.profile

import domain.model.User

data class ProfileScreenUiState(
    val currentUser: User? = null,
    val isLoading: Boolean = true,
    val deleteUserDialogShown: Boolean = false,
    val reAuthenticateUserViewShown: Boolean = false,
)