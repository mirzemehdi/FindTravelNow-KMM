package presentation.screens.account.profile

import data.repository.UserRepository
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.theme.strings.Strings
import util.UiStateHolder
import util.uiStateHolderScope

class ProfileUiStateHolder(private val userRepository: UserRepository) : UiStateHolder() {

    private val _profileScreenUiState = MutableStateFlow(ProfileScreenUiState(isLoading = true))
    val profileScreenUiState =
        combine(_profileScreenUiState, userRepository.currentUser) { uiState, user ->
            val newUser =
                user?.copy(displayName = user.displayName.ifEmpty { Strings.display_name_default })
            uiState.copy(currentUser = newUser, isLoading = false)
        }
            .stateIn(
                uiStateHolderScope,
                SharingStarted.WhileSubscribed(5000),
                ProfileScreenUiState(isLoading = true)
            )

    fun onClickLogOut() = uiStateHolderScope.launch {
        userRepository.logOut()
    }

    fun onClickDeleteAccount() = uiStateHolderScope.launch {
        _profileScreenUiState.update { it.copy(deleteUserDialogShown = true) }
    }

    fun onDismissDeleteUserConfirmationDialog() = uiStateHolderScope.launch {
        _profileScreenUiState.update { it.copy(deleteUserDialogShown = false) }
    }

    fun onConfirmDeleteAccount() = uiStateHolderScope.launch {
        _profileScreenUiState.update {
            it.copy(
                reAuthenticateUserViewShown = true,
                deleteUserDialogShown = false,
                currentUserAuthProviderList = userRepository.getCurrentUserProviders()
            )
        }
    }

    fun onDismissReAuthenticateView() = uiStateHolderScope.launch {
        _profileScreenUiState.update { it.copy(reAuthenticateUserViewShown = false) }
    }

    fun onMessageIsShown() = uiStateHolderScope.launch {
        _profileScreenUiState.update { it.copy(message = null) }
    }

    fun onUserReAuthenticatedResult(result: Result<FirebaseUser?>) = uiStateHolderScope.launch {
        _profileScreenUiState.update { it.copy(isDeleteInProgress = true) }
        result.onSuccess { user ->
            val userMessage = if (user != null) {
                userRepository.deleteAccount()
                null
            } else
                Strings.error_msg_no_signed_in_user
            _profileScreenUiState.update {
                it.copy(
                    isDeleteInProgress = false,
                    reAuthenticateUserViewShown = false,
                    message = userMessage
                )
            }

        }.onFailure { error ->
            _profileScreenUiState.update {
                it.copy(
                    isDeleteInProgress = false,
                    reAuthenticateUserViewShown = false,
                    message = error.message
                )
            }
        }

    }


}