package onboarding

data class OnBoardingScreenData(val title: String, val description: String, val imageRes: String)
data class OnBoardingScreenUiState(
    val pages: List<OnBoardingScreenData> = listOf(
        OnBoardingScreenData("Trusted and free","We’re completely free to use – trusted by thousands people.","drawable/onboarding_1.xml"),
        OnBoardingScreenData("Book with flexibility","Search easily and find flights with no change fees.","drawable/onboarding_2.xml"),
        OnBoardingScreenData("Explore great deals","Search 100s of travel sites to compare prices.","drawable/onboarding_3.xml"),
    )
)