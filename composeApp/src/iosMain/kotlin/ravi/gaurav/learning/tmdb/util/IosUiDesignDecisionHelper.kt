package ravi.gaurav.learning.tmdb.util

import androidx.compose.runtime.Composable

class IosUiDesignDecisionHelper: UiDesignDecisionHelper {

    @Composable
    override fun shouldAddNavigationBarPadding(): Boolean {
        return false
    }
}