package presentation.screens.home

import domain.model.FlightInfo
import presentation.theme.strings.Strings

data class HomeScreenUiState(
    val categories: List<CategoryData> = listOf(
        CategoryData(
            title = Strings.category_flight,
            iconRes = "drawable/ic_category_flight.xml",
            url = "https://appfindtravelnow.blogspot.com/p/flight.html"

        ),
        CategoryData(
            title = Strings.category_hotel,
            iconRes = "drawable/ic_category_hotel.xml",
            url = "https://appfindtravelnow.blogspot.com/p/hotel.html"
        ),
        CategoryData(title=Strings.category_car, iconRes = "drawable/ic_category_car.xml",url = "https://appfindtravelnow.blogspot.com/p/car.html"),
        CategoryData(title=Strings.category_taxi, iconRes ="drawable/ic_category_taxi.xml",url = "https://appfindtravelnow.blogspot.com/p/taxi.html"),
        CategoryData(title=Strings.category_bike, iconRes ="drawable/ic_category_bike.xml",url = "https://appfindtravelnow.blogspot.com/p/bike.html"),
        CategoryData(title=Strings.category_simcard, iconRes ="drawable/ic_category_sim.xml",url = "https://drimsim.tp.st/XyHi4PxG"),
    ),
    val topFlightInfoList: List<FlightInfo> = listOf(FlightInfo(), FlightInfo()),
)

data class CategoryData(
    val title: String = "",
    val iconRes: String = "",
    val url: String = "",
)