package presentation.screens.home

import domain.model.FlightInfo
import presentation.theme.strings.Strings

data class HomeScreenUiState(
    val categories: List<CategoryData> = listOf(
        CategoryData(Strings.category_flight, "drawable/ic_category_flight.xml"),
        CategoryData(Strings.category_hotel, "drawable/ic_category_hotel.xml"),
        CategoryData(Strings.category_car, "drawable/ic_category_car.xml"),
        CategoryData(Strings.category_taxi, "drawable/ic_category_taxi.xml"),
        CategoryData(Strings.category_bike, "drawable/ic_category_bike.xml"),
        CategoryData(Strings.category_simcard, "drawable/ic_category_sim.xml"),
    ),
    val topFlightInfoList: List<FlightInfo> = listOf(FlightInfo(), FlightInfo()),
)

data class CategoryData(
    val title: String = "",
    val iconRes: String = "",
    val url: String = "",
)