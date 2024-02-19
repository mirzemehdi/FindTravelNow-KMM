package data.source.remote.request

import data.source.remote.response.FlightInfoResponse
import domain.mapper.DomainMapper
import domain.model.FlightLocation
import domain.model.Top5Flights
import domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    @SerialName("displayName") val displayName: String? = "",
    @SerialName("profilePicUrl") val profilePicUrl: String? = "",
)