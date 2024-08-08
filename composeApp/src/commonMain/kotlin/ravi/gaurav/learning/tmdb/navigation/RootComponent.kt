package ravi.gaurav.learning.tmdb.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable
import ravi.gaurav.learning.tmdb.domain.Movie

class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val navigator = StackNavigation<Config>()

    val childStack = childStack(
        source = navigator,
        serializer = Config.serializer(),
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Config,
        context: ComponentContext
    ): Child = when (config) {
        is Config.Main -> Child.Main(
            MainComponent(context) { movieId ->
                navigator.pushNew(Config.Detail(movieId))
            }
        )

        is Config.Detail -> Child.Detail(
            DetailComponent(
                context,
                config.movieId,
                onBack = { navigator.pop() },
                onMovieSelected = { movieId -> navigator.pushNew(Config.Detail(movieId)) }
            )
        )
    }

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
        data class Detail(val component: DetailComponent) : Child()
    }

    @Serializable
    sealed class Config {
        @Serializable
        data object Main : Config()

        @Serializable
        data class Detail(val movieId: Long, val uniqueId: String = randomUUID()) : Config()
    }
}