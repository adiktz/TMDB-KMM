package ravi.gaurav.learning.tmdb.util

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
actual fun getScreenDimensionsHelper(): ScreenDimensionsHelper = object : ScreenDimensionsHelper {
    val screenSize = UIScreen.mainScreen.bounds.useContents { size.width to size.height }

    @Composable
    override fun getScreenHeight(): Int {
        return screenSize.second.toInt()
    }

    @Composable
    override fun getScreenWidth(): Int {
        return screenSize.first.toInt()
    }
}

@OptIn(ExperimentalForeignApi::class)
class ScreenDimensionsHelperImpl : ScreenDimensionsHelper {

    @Composable
    override fun getScreenHeight(): Int {
        val screenSize = UIScreen.mainScreen.bounds.useContents { size.width to size.height }
        return screenSize.second.toInt()
    }

    @Composable
    override fun getScreenWidth(): Int {
        val screenSize = UIScreen.mainScreen.bounds.useContents { size.width to size.height }
        return screenSize.first.toInt()
    }
}