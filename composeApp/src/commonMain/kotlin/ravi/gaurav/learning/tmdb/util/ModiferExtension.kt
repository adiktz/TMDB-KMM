package ravi.gaurav.learning.tmdb.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun Modifier.safeCutOutPadding(): Modifier {
    val insetsHelper: SystemInsetsHelper = getSystemInsetsHelper()
    return when {
        !insetsHelper.isPortraitMode() -> {
            val paddingValues = maxOf(
                WindowInsets.displayCutout.asPaddingValues().calculateLeftPadding(LayoutDirection.Ltr),
                WindowInsets.displayCutout.asPaddingValues().calculateRightPadding(LayoutDirection.Ltr)
            )
            this.padding(start = paddingValues, end = paddingValues)
        }
        else -> {
            this
        }
    }
}

@Composable
fun Modifier.safeHeaderPadding(): Modifier {
    return this
        .statusBarsPadding()
        .safeCutOutPadding()
}