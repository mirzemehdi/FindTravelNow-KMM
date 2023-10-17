package domain.model

import presentation.theme.strings.Strings
import util.extensions.toFormattedDate

data class FlightInfo(
    val date: String = "",
    val priceWithCurrency: String = "",
    val origin: FlightLocation = FlightLocation(),
    val destination: FlightLocation = FlightLocation(),
) {
    fun getUrl(): String {
        val urlArgs = "${origin.iataCode}${date.toFormattedDate("ddMM")}${destination.iataCode}1"
        return "${Strings.web_url_search_flight}$urlArgs"
    }
}