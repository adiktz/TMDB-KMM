import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.jetbrains.compose.resources.painterResource
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.di.initKoinDesktop
import ravi.gaurav.learning.tmdb.di.platformComponent.DesktopComponent
import ravi.gaurav.learning.tmdb.navigation.RootComponent
import tmdb.composeapp.generated.resources.Res
import tmdb.composeapp.generated.resources.compose_multiplatform


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
        val state = rememberWindowState( size = DpSize(768.dp, 1280.dp)
        )

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = state,
            title = "The Movie Database",
        ) {
            MaterialTheme {
                Surface {
                    App(root = root)
                }
            }
        }
    }
}

@Composable
fun WindowScope.AppWindowTitleBar(onMinimize: () -> Unit, onClose: () -> Unit) {
    AppDraggableArea()
    Box(Modifier.fillMaxWidth()) {
        Row(Modifier.align(Alignment.TopEnd).padding(horizontal = 8.dp)) {
            IconButton(onClick = onMinimize) {
                // minimize button
            }
            IconButton(onClick = onClose) {
                Icon(painterResource(Res.drawable.compose_multiplatform), null)
            }
        }
    }
}

@Composable
private fun WindowScope.AppDraggableArea() = WindowDraggableArea {
    Box(
        Modifier.fillMaxWidth().height(18.dp)/*.offset(y = 2.dp)*/
            .padding(horizontal = 160.dp)
            .shadow(4.dp, RoundedCornerShape(4.dp, 4.dp, 12.dp, 12.dp), ambientColor = Color.White)
            .background(Color.Blue)
    )
}