package ravi.gaurav.learning.tmdb.util

import androidx.compose.runtime.Composable
import java.awt.Toolkit

actual fun getScreenDimensionsHelper(): ScreenDimensionsHelper  = object : ScreenDimensionsHelper {
    val screenSize = Toolkit.getDefaultToolkit().screenSize

    @Composable
    override fun getScreenHeight(): Int = screenSize.height

    @Composable
    override fun getScreenWidth(): Int = screenSize.width
}

class DesktopScreenDimensionsHelper: ScreenDimensionsHelper {

    @Composable
    override fun getScreenHeight(): Int = Toolkit.getDefaultToolkit().screenSize.height

    @Composable
    override fun getScreenWidth(): Int = Toolkit.getDefaultToolkit().screenSize.width
}