package presentation.screens.top5flights

import domain.model.FlightInfo
import domain.model.FlightLocation
import domain.model.FlightSort

data class Top5FlightsUiState(
    val flights: List<FlightInfo> = emptyList(),
    val origin: FlightLocation = FlightLocation.getDefault(),
    val isLoading: Boolean = true,
    val maxPrice: Int = 50,
    val sortBy: FlightSort? = null,
    val nextUpdateInDays: Int = 0,
    val lastUpdateDate: String = "",
)