import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import ravi.gaurav.learning.tmdb.navigation.RootComponent

fun MainViewController() = ComposeUIViewController {
    val root = remember {
        RootComponent(DefaultComponentContext(ApplicationLifecycle()))
    }
    App(root)
}