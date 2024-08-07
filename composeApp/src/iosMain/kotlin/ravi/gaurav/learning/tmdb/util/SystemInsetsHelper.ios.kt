package ravi.gaurav.learning.tmdb.util

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import platform.UIKit.UIApplication
import platform.UIKit.UIInterfaceOrientationPortrait

actual fun getSystemInsetsHelper(): SystemInsetsHelper = object : SystemInsetsHelper {
    @Composable
    override fun isPortraitMode(): Boolean {
        // iOS specific implementation to check orientation
        // Use UIKit or SwiftUI to determine orientation
        val orientation = UIApplication.sharedApplication.statusBarOrientation
        return orientation == UIInterfaceOrientationPortrait
    }

    @Composable
    override fun getPaddingModifier(): Modifier {
        // iOS specific implementation to apply padding based on orientation
        // Example (not complete):
        return if (isPortraitMode()) {
            Modifier
        } else {
            Modifier.systemBarsPadding()
        }
    }

    override val os: OS
        get() = OS.IOS
}