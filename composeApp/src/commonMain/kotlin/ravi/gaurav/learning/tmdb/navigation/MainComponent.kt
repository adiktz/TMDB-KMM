package ravi.gaurav.learning.tmdb.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    init {
        getCensoredText()
    }
    fun getKtor() {
        scope.launch(Dispatchers.Main) {
            val response = repo.getKtor()
            response.onSuccess {
                _text.value = it
            }
            response.onFailure {
                _text.value = it.message ?: "Something went wrong...!!!"
            }
        }
    }

    fun getCensoredText() {
        scope.launch(Dispatchers.Main) {
            val response = repo.getCensoredText()
            response.onSuccess {
                _text.value = it.result
            }
            response.onFailure {
                _text.value = it.message ?: "Something went wrong...!!!"
            }
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.showDetails -> {
                showDetails(text.value)
            }
            is MainEvent.update -> {
                _text.value = event.id
            }
        }
    }
}

sealed interface MainEvent {
    data class update(val id: String) : MainEvent
    data object showDetails : MainEvent
}