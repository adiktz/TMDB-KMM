package ravi.gaurav.learning.tmdb.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ravi.gaurav.learning.tmdb.api.Repository

class MainComponent(
    componentContext: ComponentContext,
    val showDetails: (String) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val repo: Repository by inject()

    private var _text: MutableStateFlow<String> = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    private val scope = coroutineScope(SupervisorJob())

    private var _channel = Channel<String>(Channel.CONFLATED)
    val channel get() = _channel.receiveAsFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        scope.launch {
            val response = repo.getPopularMovies()
            response.onSuccess {
                _text.value = it
            }
            response.onFailure {
                println(it.message)
            }
        }
    }
    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ShowDetails -> {
                showDetails(text.value)
            }
            is MainEvent.Update -> {
                _text.value = event.id
            }
        }
    }
}

sealed interface MainEvent {
    data class Update(val id: String) : MainEvent
    data object ShowDetails : MainEvent
}