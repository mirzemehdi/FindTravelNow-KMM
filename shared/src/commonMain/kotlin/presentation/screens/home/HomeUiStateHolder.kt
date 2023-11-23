package presentation.screens.home

import data.repository.FlightsRepository
import domain.model.FlightLocation
import domain.model.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import util.UiStateHolder
import util.logging.AppLogger
import util.uiStateHolderScope

class HomeUiStateHolder(private val flightsRepository: FlightsRepository) : UiStateHolder() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        AppLogger.e("UiStateHolder is initialized")
        getTop5Flights()
    }


    private fun getTop5Flights() = uiStateHolderScope.launch {

        with(_uiState.value) {
            flightsRepository.getTop5Flights(origin = null)
                .onSuccess { resultData ->
                    _uiState.update {
                        it.copy(topFlightInfoList = resultData.flights.take(2))
                    }
                }
        }
    }

}