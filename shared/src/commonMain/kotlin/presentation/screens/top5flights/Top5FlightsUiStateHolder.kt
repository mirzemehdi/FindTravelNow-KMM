package presentation.screens.top5flights

import data.repository.FlightsRepository
import domain.model.FlightSort
import domain.model.result.onError
import domain.model.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toInstant
import util.UiStateHolder
import util.extensions.toLocalDateString
import util.logging.AppLogger
import util.uiStateHolderScope

class Top5FlightsUiStateHolder(private val flightsRepository: FlightsRepository) : UiStateHolder() {
    private val _uiState = MutableStateFlow(Top5FlightsUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        AppLogger.d("UiStateHolder is initialized")
        getTop5Flights()
    }

    fun onSelectSort(flightSort: FlightSort) {
        _uiState.update { it.copy(sortBy = flightSort) }
        getTop5Flights()
    }

    private fun getTop5Flights() = uiStateHolderScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        with(_uiState.value) {
            flightsRepository.getTop5Flights(
                origin = origin,
                maxPrice = maxPrice,
                sortBy = sortBy ?: FlightSort.BY_PRICE
            )
                .onSuccess { resultData ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            flights = resultData.flights,
                            lastUpdateDate = resultData.lastUpdateDate
                                .toInstant().toLocalDateString(),
                            nextUpdateInDays = resultData.lastUpdateDate.toInstant()
                                .daysUntil(resultData.nextUpdateDate.toInstant(), TimeZone.UTC)

                        )
                    }
                }
                .onError { error ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
        }
    }

}