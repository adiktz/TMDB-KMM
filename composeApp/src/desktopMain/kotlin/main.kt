import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.get
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import ravi.gaurav.learning.tmdb.di.initKoinDesktop
import ravi.gaurav.learning.tmdb.di.platformComponent.DesktopComponent
import ravi.gaurav.learning.tmdb.navigation.RootComponent


fun main() {

    val lifecycle = LifecycleRegistry()

    val koin = initKoinDesktop(
        additionalModules = listOf(
            module { single { DesktopComponent()} },
        //    module { single<ComponentContext> { DefaultComponentContext(lifecycle) } },
        )
    )

//    val lifecycle = LifecycleRegistry()

    val root =
        runOnUiThread {
            RootComponent(
                componentContext = DefaultComponentContext(lifecycle)
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