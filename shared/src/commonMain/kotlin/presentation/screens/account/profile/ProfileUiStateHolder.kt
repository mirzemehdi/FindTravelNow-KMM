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
import util.logging.AppLogger
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
        _profileScreenUiState.update { it.copy(reAuthenticateUserViewShown = true, deleteUserDialogShown = false) }
    }

    fun onDismissReAuthenticateView() = uiStateHolderScope.launch {
        _profileScreenUiState.update { it.copy(reAuthenticateUserViewShown = false) }
    }

    fun onUserReAuthenticatedResult(result: Result<FirebaseUser?>) = uiStateHolderScope.launch {
        _profileScreenUiState.update { it.copy(reAuthenticateUserViewShown = false) }
        result.onSuccess {user->
            if (user!=null) userRepository.deleteAccount()
            else{
                AppLogger.e("User is null")
            }
        }.onFailure {
            AppLogger.e(it.message)
        }

    }


}