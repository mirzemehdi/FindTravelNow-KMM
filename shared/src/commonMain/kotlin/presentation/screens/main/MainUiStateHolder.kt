package presentation.screens.main

import data.repository.GlobalAppRepository
import domain.model.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import util.UiStateHolder
import util.uiStateHolderScope

class MainUiStateHolder(private val globalAppRepository: GlobalAppRepository) : UiStateHolder() {

    private val _uiState =
        MutableStateFlow(MainScreenUiState(isAppVersionUpgradeRequired = false))
    val uiState = _uiState.asStateFlow()


    init {
        getGlobalConfig()
    }

    private fun getGlobalConfig() = uiStateHolderScope.launch {
        globalAppRepository.getGlobalConfig().onSuccess {appConfig->
            _uiState.update { it.copy(isAppVersionUpgradeRequired = appConfig.isUpdateRequired) }
        }

    }

}