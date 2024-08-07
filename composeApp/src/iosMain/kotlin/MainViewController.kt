import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import ravi.gaurav.learning.tmdb.navigation.RootComponent

fun MainViewController() = ComposeUIViewController {
    val applicationLifeCycle = ApplicationLifecycle()
    loadKoinModules(
        listOf(
            module { single<ComponentContext> { DefaultComponentContext(applicationLifeCycle) } },
            module { single<RootComponent> { RootComponent(get()) } }
        )
    )
    /*val root = remember {
        RootComponent(DefaultComponentContext(ApplicationLifecycle()))
    }*/
    App(root = getKoin().get())
}