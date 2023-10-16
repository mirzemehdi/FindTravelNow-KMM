package domain.model

data class FlightLocation(
    val city: String = "",
    val country: String = "",
    val iataCode: String = "",
) {
    companion object {
        fun getDefault() = FlightLocation(city = "Budapest", "Hungary", "BUD")
    }
}