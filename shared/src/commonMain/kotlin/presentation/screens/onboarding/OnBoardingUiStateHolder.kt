package presentation.screens.onboarding

import data.source.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import util.UiStateHolder
import util.uiStateHolderScope

class OnBoardingUiStateHolder(private val userPreferences: UserPreferences) : UiStateHolder() {

    private val _uiState = MutableStateFlow(OnBoardingScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onClickNavigateNext() = uiStateHolderScope.launch {
        if (_uiState.value.isPrivacyPolicyChecked.not()) return@launch
        userPreferences.putBoolean(UserPreferences.KEY_IS_ONBOARD_SHOWN, true)
        _uiState.update { it.copy(onBoardIsShown = true) }
    }

    fun onCheckPrivacyPolicy() {
        _uiState.update { it.copy(isPrivacyPolicyChecked = it.isPrivacyPolicyChecked.not()) }
    }
}