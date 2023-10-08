package presentation.screens.onboarding

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import util.UiStateHolder

class OnBoardingStateHolder() : UiStateHolder() {

    private val _uiState = MutableStateFlow(OnBoardingScreenUiState())
    val uiState = _uiState.asStateFlow()
}