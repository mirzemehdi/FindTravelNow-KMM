package data.source.remote.apiservice

import data.source.remote.response.ApiResponse
import data.source.remote.response.GlobalAppConfigResponse
import data.source.remote.response.TopFlightPricesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import util.config.AppConfig

class GlobalApiService(private val httpClient: HttpClient) {

    suspend fun getGlobalConfigInfo(): ApiResponse<GlobalAppConfigResponse> = httpClient.get(ApiEndPoints.globalConfig) {
        url {
            parameters.append("app_version_code", AppConfig.versionCode.toString())
        }
    }.body()


}