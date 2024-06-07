package presentation.screens.home

import domain.model.FlightInfo
import findtravelnow_kmm.shared.generated.resources.Res
import findtravelnow_kmm.shared.generated.resources.ic_category_bike
import findtravelnow_kmm.shared.generated.resources.ic_category_car
import findtravelnow_kmm.shared.generated.resources.ic_category_flight
import findtravelnow_kmm.shared.generated.resources.ic_category_hotel
import findtravelnow_kmm.shared.generated.resources.ic_category_sim
import findtravelnow_kmm.shared.generated.resources.ic_category_taxi
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.theme.strings.Strings

data class HomeScreenUiState @OptIn(ExperimentalResourceApi::class) constructor(
    val categories: List<CategoryData> = listOf(
        CategoryData(
            title = Strings.category_flight,
            iconRes = Res.drawable.ic_category_flight,
            url = "https://appfindtravelnow.blogspot.com/p/flight.html"

        ),
        CategoryData(
            title = Strings.category_hotel,
            iconRes = Res.drawable.ic_category_hotel,
            url = "https://appfindtravelnow.blogspot.com/p/hotel.html"
        ),
        CategoryData(
            title = Strings.category_car,
            iconRes = Res.drawable.ic_category_car,
            url = "https://appfindtravelnow.blogspot.com/p/car.html"
        ),
        CategoryData(
            title = Strings.category_taxi,
            iconRes = Res.drawable.ic_category_taxi,
            url = "https://appfindtravelnow.blogspot.com/p/taxi.html"
        ),
        CategoryData(
            title = Strings.category_bike,
            iconRes = Res.drawable.ic_category_bike,
            url = "https://appfindtravelnow.blogspot.com/p/bike.html"
        ),
        CategoryData(
            title = Strings.category_simcard,
            iconRes = Res.drawable.ic_category_sim,
            url = "https://appfindtravelnow.blogspot.com/p/esim.html"
        ),
    ),
    val topFlightInfoList: List<FlightInfo> = listOf(),
)

data class CategoryData @OptIn(ExperimentalResourceApi::class) constructor(
    val title: String = "",
    val iconRes: DrawableResource,
    val url: String = "",
)