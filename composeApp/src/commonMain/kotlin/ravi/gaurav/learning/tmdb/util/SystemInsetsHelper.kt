package ravi.gaurav.learning.tmdb.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Common
interface SystemInsetsHelper {
    @Composable
    fun isPortraitMode(): Boolean
    @Composable
    fun getPaddingModifier(): Modifier

    val os: OS
}

// Common
expect fun getSystemInsetsHelper(): SystemInsetsHelper

enum class OS {
    ANDROID, IOS, DESKTOP
}