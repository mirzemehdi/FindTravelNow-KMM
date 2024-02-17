package data.source.remote.response

import data.source.remote.response.FlightInfoResponse
import domain.mapper.DomainMapper
import domain.model.FlightLocation
import domain.model.Top5Flights
import domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("id") val id: Int,
    @SerialName("firebase_uid") val firebaseUserId: String? = "",
    @SerialName("displayName") val displayName: String? = "",
    @SerialName("email") val email: String? = "",
    @SerialName("profilePicUrl") val profilePicUrl: String? = "",
    @SerialName("referralCode") val referralCode: String? = "",
) : DomainMapper<User> {
    override fun mapToDomainModel(): User {
        return User(
            id = "$id",
            displayName = displayName ?: "",
            profilePicSrc = profilePicUrl,
            email = email ?: ""
        )
    }
}