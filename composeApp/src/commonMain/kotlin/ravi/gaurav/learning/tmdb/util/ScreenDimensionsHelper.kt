package ravi.gaurav.learning.tmdb.util

import androidx.compose.runtime.Composable

// Common
interface ScreenDimensionsHelper {
    @Composable
    fun getScreenHeight(): Int

    @Composable
    fun getScreenWidth(): Int
}

// Common
expect fun getScreenDimensionsHelper(): ScreenDimensionsHelper