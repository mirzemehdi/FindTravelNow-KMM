package domain.model

data class FlightInfo(
    val date: String = "",
    val priceWithCurrency: String = "",
    val origin: FlightLocation = FlightLocation(),
    val destination: FlightLocation = FlightLocation(),
)