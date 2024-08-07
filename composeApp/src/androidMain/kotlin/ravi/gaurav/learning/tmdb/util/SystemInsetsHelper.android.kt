package ravi.gaurav.learning.tmdb.util

import android.content.res.Configuration
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

actual fun getSystemInsetsHelper(): SystemInsetsHelper = object : SystemInsetsHelper {
    @Composable
    override fun isPortraitMode(): Boolean {
        val configuration = LocalConfiguration.current
        return configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    @Composable
    override fun getPaddingModifier(): Modifier {
        val configuration = LocalConfiguration.current
        return if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Modifier
        } else {
            Modifier.systemBarsPadding()
        }
    }

    override val os: OS
        get() = OS.ANDROID
}