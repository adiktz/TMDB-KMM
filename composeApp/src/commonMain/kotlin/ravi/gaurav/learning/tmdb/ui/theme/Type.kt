package ravi.gaurav.learning.tmdb.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import tmdb.composeapp.generated.resources.Res
import tmdb.composeapp.generated.resources.nunito_bold
import tmdb.composeapp.generated.resources.nunito_regular
import tmdb.composeapp.generated.resources.nunito_semibold
import tmdb.composeapp.generated.resources.ubuntu_bold
import tmdb.composeapp.generated.resources.ubuntu_light
import tmdb.composeapp.generated.resources.ubuntu_regular

@Composable
fun displayFontFamily() = FontFamily(
    Font(Res.font.nunito_regular, FontWeight.Normal),
    Font(Res.font.nunito_semibold, FontWeight.SemiBold),
    Font(Res.font.nunito_bold, FontWeight.Bold)
)

@Composable
fun bodyFontFamily() = FontFamily(
    Font(Res.font.ubuntu_light, FontWeight.Light),
    Font(Res.font.ubuntu_regular, FontWeight.Normal),
    Font(Res.font.ubuntu_bold, FontWeight.SemiBold),
    Font(Res.font.ubuntu_bold, FontWeight.Bold)
)

@Composable
fun AppTypography() = Typography().run {

    val displayFamily = displayFontFamily()
    val bodyFamily = bodyFontFamily()

    copy(
        displayLarge = displayLarge.copy(fontFamily = displayFamily),
        displayMedium = displayMedium.copy(fontFamily = displayFamily),
        displaySmall = displaySmall.copy(fontFamily = displayFamily),
        headlineLarge = headlineLarge.copy(fontFamily = displayFamily),
        headlineMedium = headlineMedium.copy(fontFamily = displayFamily),
        headlineSmall = headlineSmall.copy(fontFamily = displayFamily),
        titleLarge = titleLarge.copy(fontFamily = displayFamily),
        titleMedium = titleMedium.copy(fontFamily = displayFamily),
        titleSmall = titleSmall.copy(fontFamily = displayFamily),
        bodyLarge = bodyLarge.copy(fontFamily = bodyFamily),
        bodyMedium = bodyMedium.copy(fontFamily = bodyFamily),
        bodySmall = bodySmall.copy(fontFamily = bodyFamily),
        labelLarge = labelLarge.copy(fontFamily = bodyFamily),
        labelMedium = labelMedium.copy(fontFamily = bodyFamily),
        labelSmall = labelSmall.copy(fontFamily = bodyFamily)
    )
}

