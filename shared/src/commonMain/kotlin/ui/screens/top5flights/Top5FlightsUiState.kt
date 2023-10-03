package ui.screens.top5flights

import domain.model.FlightInfo

data class Top5FlightsUiState(
    val flights: List<FlightInfo> = listOf(
        FlightInfo(),
        FlightInfo(),
        FlightInfo(),
        FlightInfo(),
        FlightInfo(),
        FlightInfo(),
        FlightInfo(),
        FlightInfo(),
        FlightInfo(),
        FlightInfo(),
        FlightInfo(),

    ),
)