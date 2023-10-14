package data.source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T : Any?>(
    @SerialName("responseCode") val responseCode: Int,
    @SerialName("errorMessage") val errorMessage: String? = null,
    @SerialName("data") val data: T? = null,
) {
    fun isSuccessful() = responseCode in 200..299
    fun hasSuccessfulData() = isSuccessful() && data != null
    fun getSuccessfulData(): T {
        if (hasSuccessfulData()) return data!!
        else throw Exception("ApiResponse Data is null. " +
                "Make sure you checked value of  hasSuccessfulData() function before getting data")
    }

}

