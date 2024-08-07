package ravi.gaurav.learning.tmdb.util

import androidx.compose.runtime.Composable

class DesktopUiDesignDecisionHelper: UiDesignDecisionHelper {

    @Composable
    override fun shouldAddNavigationBarPadding(): Boolean {
        return true
    }
}