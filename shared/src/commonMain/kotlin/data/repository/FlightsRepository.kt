package data.repository

import data.BackgroundExecutor
import data.source.remote.apiservice.FlightsApiService
import domain.model.FlightLocation
import domain.model.FlightSort
import domain.model.Top5Flights
import domain.model.result.ErrorEntity
import domain.model.result.Result

class FlightsRepository(
    private val flightsApiService: FlightsApiService,
    private val backgroundExecutor: BackgroundExecutor = BackgroundExecutor.IO,
) {

    suspend fun getTop5Flights(
        origin: FlightLocation? = null,
        maxPrice: Int = 50,
        sortBy: FlightSort = FlightSort.BY_PRICE,
    ): Result<Top5Flights> = backgroundExecutor.execute {

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

    }

}