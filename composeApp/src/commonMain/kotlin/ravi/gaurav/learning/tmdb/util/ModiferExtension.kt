package ravi.gaurav.learning.tmdb.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.safeCutOutPadding(): Modifier {
    val insetsHelper: SystemInsetsHelper = getSystemInsetsHelper()
    return when {
        !insetsHelper.isPortraitMode() -> {
            val paddingValues = maxOf(
                WindowInsets.displayCutout.asPaddingValues().calculateLeftPadding(LayoutDirection.Ltr),
                WindowInsets.displayCutout.asPaddingValues().calculateRightPadding(LayoutDirection.Ltr)
            )
            if (insetsHelper.os == OS.ANDROID) {
                this.padding(start = 15.dp + paddingValues, end = paddingValues)
            } else {
                this.padding(start = paddingValues, end = paddingValues)
            }
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