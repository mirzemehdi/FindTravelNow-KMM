package data.repository

import data.source.remote.apiservice.FlightsApiService
import domain.model.FlightInfo
import domain.model.result.ErrorEntity
import domain.model.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FlightsRepository(
    private val flightsApiService: FlightsApiService,
    private val backgroundScope: CoroutineContext = Dispatchers.IO,
) {

    suspend fun getTop5Flights(
        origin: String,
        maxPrice: String,
        sortBy: String,
    ): Result<List<FlightInfo>> = withContext(backgroundScope) {
        try {
            val apiResponse = flightsApiService.getTop5Flights(origin, maxPrice, sortBy)
            if (apiResponse.hasSuccessfulData())
                Result.success(emptyList())//TODO FIX this
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