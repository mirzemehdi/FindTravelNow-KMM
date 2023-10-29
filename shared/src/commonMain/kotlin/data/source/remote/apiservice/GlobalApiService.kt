package data.source.remote.apiservice

import data.source.remote.response.ApiResponse
import data.source.remote.response.GlobalAppConfigResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import util.AppVersion

class GlobalApiService(private val httpClient: HttpClient,private val appVersion: AppVersion) {

    suspend fun getGlobalConfigInfo(): ApiResponse<GlobalAppConfigResponse> = httpClient.get(ApiEndPoints.globalConfig) {
        url {
            parameters.append("app_version_code", appVersion.code())
        }
    }.body()


}