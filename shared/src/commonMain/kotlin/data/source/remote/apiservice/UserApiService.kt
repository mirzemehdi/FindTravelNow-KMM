package data.source.remote.apiservice

import data.source.remote.request.UserUpdateRequest
import data.source.remote.response.ApiResponse
import data.source.remote.response.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class UserApiService(private val httpClient: HttpClient) {

    suspend fun createOrGetUser(): ApiResponse<UserResponse> =
        httpClient.post(ApiEndPoints.users).body()

    suspend fun updateUser(userUpdateRequest: UserUpdateRequest): ApiResponse<UserResponse> =
        httpClient.put(ApiEndPoints.users) {
            setBody(userUpdateRequest)
        }.body()
}