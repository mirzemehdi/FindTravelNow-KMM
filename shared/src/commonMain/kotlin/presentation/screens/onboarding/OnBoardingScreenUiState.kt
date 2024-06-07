package presentation.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import findtravelnow_kmm.shared.generated.resources.Res
import findtravelnow_kmm.shared.generated.resources.onboarding_1
import findtravelnow_kmm.shared.generated.resources.onboarding_2
import findtravelnow_kmm.shared.generated.resources.onboarding_3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.theme.strings.Strings

data class OnBoardingScreenData @OptIn(ExperimentalResourceApi::class) constructor(val title: String, val description: String, val imageRes: DrawableResource)
data class OnBoardingScreenUiState @OptIn(ExperimentalFoundationApi::class,
    ExperimentalResourceApi::class
) constructor(
    val pages: List<OnBoardingScreenData> = listOf(
        OnBoardingScreenData(
            Strings.onboarding_title_1,
            Strings.onboarding_desc_1,
            Res.drawable.onboarding_1
        ),
        OnBoardingScreenData(
            Strings.onboarding_title_2,
            Strings.onboarding_desc_2,
            Res.drawable.onboarding_2
        ),
        OnBoardingScreenData(
            Strings.onboarding_title_3,
            Strings.onboarding_desc_3,
            Res.drawable.onboarding_3
        ),
    ),
    val onBoardIsShown: Boolean = false,
    val isPrivacyPolicyChecked: Boolean = false,
)