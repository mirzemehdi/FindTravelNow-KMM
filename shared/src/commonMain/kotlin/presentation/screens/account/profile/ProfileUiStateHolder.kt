package presentation.screens.account.profile

import data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import presentation.theme.strings.Strings
import util.UiStateHolder
import util.uiStateHolderScope

class ProfileUiStateHolder(private val userRepository: UserRepository) : UiStateHolder() {

    val profileScreenUiState = userRepository
        .currentUser
        .map { user ->
            val newUser =
                user?.copy(displayName = user.displayName.ifEmpty { Strings.display_name_default })
            ProfileScreenUiState(currentUser = newUser, isLoading = false)
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
        userRepository.deleteAccount()
    }

}