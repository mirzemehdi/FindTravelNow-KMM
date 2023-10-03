package domain.model

data class FlightInfo(
    val date:String="2023-11-07",
    val price:String="$16.37",
    val originCode:String="BUD",
    val originCity:String="Budapest",
    val originCountry:String="Hungary",
    val destinationCode:String="ROM",
    val destinationCity:String="Rome",
    val destinationCountry:String="Italy",
)