import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import ravi.gaurav.learning.tmdb.navigation.RootComponent

/*fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TMDB",
    ) {
        App()
    }
}*/

fun main() {
    val lifecycle = LifecycleRegistry()

    val root =
        runOnUiThread {
            RootComponent(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
            )
        }

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "TMDB"
        ) {
            MaterialTheme {
                Surface {
                    App(root = root)
                }
            }
        }
    }
}