package data.source.remote.response

import data.source.remote.response.FlightPriceResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopFlightPricesResponse(
    @SerialName("flights") val flights: List<FlightPriceResponse> = emptyList(),
    @SerialName("lastUpdateDate") val lastUpdateDate: String = "",
)