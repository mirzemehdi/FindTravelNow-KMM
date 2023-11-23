package data.repository

import data.source.remote.apiservice.FlightsApiService
import domain.model.FlightLocation
import domain.model.FlightSort
import domain.model.Top5Flights
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
        origin: FlightLocation? = null,
        maxPrice: Int = 50,
        sortBy: FlightSort = FlightSort.BY_PRICE,
    ): Result<Top5Flights> = withContext(backgroundScope) {
        try {
            val apiResponse = flightsApiService.getTop5Flights(
                origin?.iataCode, maxPrice.toString(), sortBy.value
            )
            if (apiResponse.hasSuccessfulData()) Result.success(
                apiResponse.getSuccessfulData().mapToDomainModel()
            )
            else Result.error(
                ErrorEntity.apiError(
                    errorMessage = apiResponse.errorMessage, responseCode = apiResponse.responseCode
                )
            )
        } catch (e: Exception) {
            Result.error(ErrorEntity.apiError(exception = e))
        }
    }

}