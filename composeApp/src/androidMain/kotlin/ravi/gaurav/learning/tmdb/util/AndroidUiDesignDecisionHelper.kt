package ravi.gaurav.learning.tmdb.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration

class AndroidUiDesignDecisionHelper: UiDesignDecisionHelper {
    @Composable
    override fun shouldAddNavigationBarPadding(): Boolean {
        val configuration = LocalConfiguration.current
        return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
}
