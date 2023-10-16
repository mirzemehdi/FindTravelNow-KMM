package domain.model

data class Top5Flights(
    val flights: List<FlightInfo> = emptyList(),
    val lastUpdateDate: String = "",
    val nextUpdateDate: String = ""
)