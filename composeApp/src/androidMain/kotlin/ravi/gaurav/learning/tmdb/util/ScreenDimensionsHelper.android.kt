package ravi.gaurav.learning.tmdb.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

actual fun getScreenDimensionsHelper(): ScreenDimensionsHelper = object : ScreenDimensionsHelper {
    @Composable
    override fun getScreenHeight(): Int {
        val configuration = LocalConfiguration.current
        return configuration.screenHeightDp
    }

    @Composable
    override fun getScreenWidth(): Int {
        val configuration = LocalConfiguration.current
        return configuration.screenWidthDp
    }
}

class AndroidScreenDimensionsHelper(private val context: Context): ScreenDimensionsHelper {
    @Composable
    override fun getScreenHeight(): Int {
        val configuration = LocalConfiguration.current
        return configuration.screenHeightDp
        /*return WindowMetricsCalculator
            .getOrCreate()
            .computeCurrentWindowMetrics(LocalContext.current)
            .bounds.height()*/
    }

    @Composable
    override fun getScreenWidth(): Int {
        val configuration = LocalConfiguration.current
        return configuration.screenWidthDp
        /*return WindowMetricsCalculator
            .getOrCreate()
            .computeCurrentWindowMetrics(LocalContext.current)
            .bounds.width()*/
    }

}