package ravi.gaurav.learning.tmdb.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

actual fun getSystemInsetsHelper(): SystemInsetsHelper = object : SystemInsetsHelper {
    @Composable
    override fun isPortraitMode(): Boolean {
        return false
    }

    @Composable
    override fun getPaddingModifier(): Modifier {
        return Modifier
    }

    override val os: OS
        get() = OS.DESKTOP
}