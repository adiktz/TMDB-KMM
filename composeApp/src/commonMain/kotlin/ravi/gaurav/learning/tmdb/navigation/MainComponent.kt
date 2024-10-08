package ravi.gaurav.learning.tmdb.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import ravi.gaurav.learning.tmdb.api.Repository
import ravi.gaurav.learning.tmdb.domain.Movie

class MainComponent(
    componentContext: ComponentContext,
    private val repo: Repository = getKoin().get(),
    val showDetails: (Long) -> Unit
) : ComponentContext by componentContext {


    private val scope = coroutineScope(SupervisorJob())

    private var _channel = Channel<String>(Channel.CONFLATED)
    val channel get() = _channel.receiveAsFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

    private var pageNumber: Long = 0
    private var _movies: MutableStateFlow<List<Movie>> = MutableStateFlow(arrayListOf())
    val movies = _movies.asStateFlow()

    init {
        loadMorePopularMovies()
    }

    private fun getPopularMovies() {
        if (isLoading.value) return
        scope.launch {
            _isLoading.value = true
            val response = repo.getPopularMovies(pageNumber)
            response.onSuccess { movieResponse ->
                pageNumber = movieResponse.page
                _movies.value += movieResponse.results
                delay(400)
                _isLoading.value = false
            }
            response.onFailure {
                _isLoading.value = false
                println(it.message)
            }
        }
    }

    fun loadMorePopularMovies() {
        scope.launch {
            if (!isLoading.value) {
                pageNumber += 1
                getPopularMovies()
            }
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ShowDetails -> {
                showDetails(event.movieId)
            }

            is MainEvent.Update -> {

            }
        }
    }
}

sealed interface MainEvent {
    data class Update(val id: String) : MainEvent
    data class ShowDetails(val movieId: Long) : MainEvent
}