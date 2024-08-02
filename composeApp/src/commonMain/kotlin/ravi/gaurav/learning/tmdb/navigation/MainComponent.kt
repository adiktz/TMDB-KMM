package ravi.gaurav.learning.tmdb.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainComponent(
    componentContext: ComponentContext,
    val showDetails: (String) -> Unit
) : ComponentContext by componentContext {
    private var _text: MutableStateFlow<String> = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.showDetails -> showDetails(text.value)
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