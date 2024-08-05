package ravi.gaurav.learning.tmdb.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
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
import ravi.gaurav.learning.tmdb.BuildKonfig

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
        getCensoredText()
        println(BuildKonfig.AUTH_TOKEN)
    }
    fun getKtor() {
        scope.launch(Dispatchers.Main) {
            val response = repo.getKtor()
            response.onSuccess {
                _text.value = it
            }
            response.onFailure {
                _channel.trySend(it.message ?: "Something went wrong...!!!")
            }
        }
    }

    private fun getCensoredText() {
        scope.launch(Dispatchers.Main) {
            val response = repo.getCensoredText()
            response.onSuccess {
                _text.value = it.result
                if (it.result.isEmpty()) {
                    _channel.trySend(it.error ?: "Error hogaya in 200...")
                }
            }
            response.onFailure {
                _channel.trySend(it.message ?: "Something went wrong...!!!")
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