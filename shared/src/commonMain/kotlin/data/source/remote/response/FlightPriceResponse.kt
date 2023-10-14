package data.source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlightPriceResponse(
    @SerialName("price") val price: String,
    @SerialName("originCity") val originCity: String,
    @SerialName("originCountry") val originCountry: String,
    @SerialName("originCode") val originCode: String,
    @SerialName("destinationCity") val destinationCity: String,
    @SerialName("destinationCountry") val destinationCountry: String,
    @SerialName("destinationCode") val destinationCode: String,
    @SerialName("date") val date: String,
    @SerialName("priceWithCurrency") val priceWithCurrency: String,
    @SerialName("currency") val currency: String,
    @SerialName("source") val source: String,
)