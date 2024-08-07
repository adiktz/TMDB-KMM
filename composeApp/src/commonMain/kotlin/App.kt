import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import io.kamel.image.config.LocalKamelConfig
import org.jetbrains.compose.ui.tooling.preview.Preview
import ravi.gaurav.learning.tmdb.navigation.RootComponent
import ravi.gaurav.learning.tmdb.ui.theme.TMDBTheme
import ravi.gaurav.learning.tmdb.util.customKamelConfig
import ravi.gaurav.learning.tmdb.view.DetailContent
import ravi.gaurav.learning.tmdb.view.MainContent

@Composable
@Preview
fun App(root: RootComponent) {
    CompositionLocalProvider(LocalKamelConfig provides customKamelConfig) {
        TMDBTheme {
            val childStack by root.childStack.subscribeAsState()
            Children(
                stack = childStack,
                animation = stackAnimation(slide())
            ) { children ->
                when (val child = children.instance) {
                    is RootComponent.Child.Main -> MainContent(child.component)
                    is RootComponent.Child.Detail -> DetailContent(child.component)
                }
            }
        }
    }
}