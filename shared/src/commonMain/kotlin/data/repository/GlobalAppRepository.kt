package data.repository

import data.source.remote.apiservice.FlightsApiService
import data.source.remote.apiservice.GlobalApiService
import domain.model.FlightLocation
import domain.model.FlightSort
import domain.model.GlobalAppConfig
import domain.model.Top5Flights
import domain.model.result.ErrorEntity
import domain.model.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import util.logging.AppLogger
import kotlin.coroutines.CoroutineContext

class GlobalAppRepository(
    private val globalApiService: GlobalApiService,
    private val backgroundScope: CoroutineContext = Dispatchers.IO,
) {

    suspend fun getGlobalConfig(): Result<GlobalAppConfig> = withContext(backgroundScope) {
        try {
            val apiResponse = globalApiService.getGlobalConfigInfo()
            if (apiResponse.hasSuccessfulData())
                Result.success(apiResponse.getSuccessfulData().mapToDomainModel())
            else
                Result.error(
                    ErrorEntity.apiError(
                        errorMessage = apiResponse.errorMessage,
                        responseCode = apiResponse.responseCode
                    )
                )
        } catch (e: Exception) {
            Result.error(ErrorEntity.apiError(exception = e))
        }
    }

}