package ravi.gaurav.learning.tmdb.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import org.koin.compose.koinInject

@Composable
fun Modifier.safeCutOutPadding(): Modifier {
    val insetsHelper: SystemInsetsHelper = koinInject()
    val direction = LocalLayoutDirection.current
    return when {
        !insetsHelper.isPortraitMode() -> {
            val startPadding =
                WindowInsets.displayCutout.asPaddingValues().calculateStartPadding(direction)
            val endPadding =
                WindowInsets.displayCutout.asPaddingValues().calculateEndPadding(direction)
            if (insetsHelper.os == OS.ANDROID) {
                this.padding(start = startPadding, end = endPadding)
            } else {
                this.padding(horizontal = maxOf(startPadding, endPadding))
            }
        }
        else -> {
            this
        }
    }
}

@Composable
fun Modifier.safeHeaderPadding(): Modifier {
    val insetsHelper: SystemInsetsHelper = koinInject()
    return if (insetsHelper.os == OS.IOS) {
        this
            .statusBarsPadding()
            .safeCutOutPadding()
            .safeNavigationBarsPadding()
    } else {
        this.safeContentPadding()
    }
}

@Composable
fun Modifier.safeNavigationBarsPadding(): Modifier {
    val insetsHelper: SystemInsetsHelper = koinInject()
    return if (insetsHelper.os == OS.ANDROID && !insetsHelper.isPortraitMode()) {
        this.navigationBarsPadding()
    } else {
        this
    }
}