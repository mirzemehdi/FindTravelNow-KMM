package presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import presentation.theme.Fonts.popinsFontFamily
import util.font


object Fonts {
    val popinsFontFamily
        @Composable get() = FontFamily(
            font(
                "poppins_regular",
                FontWeight.Normal,
                FontStyle.Normal
            ),
            font(
                "poppins_medium",
                FontWeight.Medium,
                FontStyle.Normal
            ),
            font(
                "poppins_semibold",
                FontWeight.SemiBold,
                FontStyle.Normal
            ),
            font(
                "poppins_bold",
                FontWeight.Bold,
                FontStyle.Normal
            ),

            )
}

val Typography
    @Composable
    get() = Typography(
        displayMedium = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = popinsFontFamily
        ),
        headlineLarge = TextStyle(
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = popinsFontFamily
        ),
        titleMedium = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = popinsFontFamily
        ),
        titleSmall = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = popinsFontFamily
        ),
        bodyMedium = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = popinsFontFamily
        ),
        bodySmall = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = popinsFontFamily
        )

    )