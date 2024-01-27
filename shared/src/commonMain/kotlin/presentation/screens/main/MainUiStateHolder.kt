package presentation.screens.main

import data.repository.GlobalAppRepository
import data.repository.UserRepository
import domain.model.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import util.UiStateHolder
import util.uiStateHolderScope

class MainUiStateHolder(
    private val globalAppRepository: GlobalAppRepository,
    userRepository: UserRepository,
) : UiStateHolder() {

    private val _uiState = MutableStateFlow(MainScreenUiState(isAppVersionUpgradeRequired = false))
    val uiState = combine(_uiState, userRepository.currentUser) { uiStateValue, currentUser ->
        uiStateValue.copy(currentUser = currentUser)
    }.stateIn(
        uiStateHolderScope,
        SharingStarted.WhileSubscribed(5000),
        MainScreenUiState(isAppVersionUpgradeRequired = false)
    )


    init {
        getGlobalConfig()

    }

    private fun getGlobalConfig() = uiStateHolderScope.launch {
        globalAppRepository.getGlobalConfig().onSuccess { appConfig ->
            _uiState.update {
                it.copy(
                    isAppVersionUpgradeRequired = appConfig.isUpdateRequired,
                )
            }
        }

    }

}