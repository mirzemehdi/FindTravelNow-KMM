package presentation.screens.onboarding

import presentation.theme.strings.Strings

data class OnBoardingScreenData(val title: String, val description: String, val imageRes: String)
data class OnBoardingScreenUiState(
    val pages: List<OnBoardingScreenData> = listOf(
        OnBoardingScreenData(
            Strings.onboarding_title_1,
            Strings.onboarding_desc_1,
            "drawable/onboarding_1.xml"
        ),
        OnBoardingScreenData(
            Strings.onboarding_title_2,
            Strings.onboarding_desc_2,
            "drawable/onboarding_2.xml"
        ),
        OnBoardingScreenData(
            Strings.onboarding_title_3,
            Strings.onboarding_desc_3,
            "drawable/onboarding_3.xml"
        ),
    ),
)