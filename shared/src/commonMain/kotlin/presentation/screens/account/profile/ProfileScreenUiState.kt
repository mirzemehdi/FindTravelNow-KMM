package presentation.screens.account.profile

import domain.model.AuthProvider
import domain.model.User

data class ProfileScreenUiState(
    val currentUser: User? = null,
    val isLoading: Boolean = true,
    val isDeleteInProgress: Boolean = false,
    val deleteUserDialogShown: Boolean = false,
    val reAuthenticateUserViewShown: Boolean = false,
    val isEditMode: Boolean = false,
    val isEditInProgress: Boolean = false,
    val currentUserAuthProviderList: List<AuthProvider> = emptyList(),
    val message: String? = null,
)