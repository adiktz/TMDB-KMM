import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.jetbrains.compose.ui.tooling.preview.Preview
import ravi.gaurav.learning.tmdb.navigation.RootComponent
import ravi.gaurav.learning.tmdb.view.DetailContent
import ravi.gaurav.learning.tmdb.view.MainContent

@Composable
@Preview
fun App(root: RootComponent) {
    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { children ->
            when(val child = children.instance) {
                is RootComponent.Child.Main -> MainContent(child.component)
                is RootComponent.Child.Detail -> DetailContent(child.component)
            }
        }
    }
}