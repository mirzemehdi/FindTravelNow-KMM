package data.source.remote.response

import domain.mapper.DomainMapper
import domain.model.FlightInfo
import domain.model.FlightLocation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlightInfoResponse(
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
):DomainMapper<FlightInfo>{
    override fun mapToDomainModel(): FlightInfo {
        return FlightInfo(
            date = date,
            priceWithCurrency = priceWithCurrency,
            origin = FlightLocation(city = originCity, country = originCountry, iataCode = originCode),
            destination = FlightLocation(city = destinationCity, country = destinationCountry, iataCode = destinationCode),
        )
    }
}