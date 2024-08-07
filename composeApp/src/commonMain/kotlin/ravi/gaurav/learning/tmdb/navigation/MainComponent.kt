package ravi.gaurav.learning.tmdb.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.mp.KoinPlatform.getKoin
import ravi.gaurav.learning.tmdb.api.Repository
import ravi.gaurav.learning.tmdb.domain.Movie

class MainComponent(
    componentContext: ComponentContext = getKoin().get(),
    private val repo: Repository = getKoin().get(),
    val showDetails: (Movie) -> Unit
) : ComponentContext by componentContext {

    private val mutex = Mutex()

    private val scope = coroutineScope(SupervisorJob())

    private var _channel = Channel<String>(Channel.CONFLATED)
    val channel get() = _channel.receiveAsFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

    private var pageNumber:Long =  0
    private var _movies: MutableStateFlow<List<Movie>> = MutableStateFlow(arrayListOf())
    val movies = _movies.asStateFlow()


    private fun getPopularMovies() {
        _isLoading.value = true
        scope.launch {
            val response = repo.getPopularMovies(pageNumber)
            response.onSuccess { movieResponse ->
                mutex.withLock {
                    pageNumber = movieResponse.page
                    _movies.value += movieResponse.results
                    _isLoading.value = false
                }
            }
            response.onFailure {
                _isLoading.value = false
                println(it.message)
            }
        }
    }

    fun loadMorePopularMovies() {
        if (!isLoading.value) {
            pageNumber += 1
            getPopularMovies()
        }

    }
    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ShowDetails -> {
                showDetails(event.movie)
            }
            is MainEvent.Update -> {

            }
        }
    }
}

sealed interface MainEvent {
    data class Update(val id: String) : MainEvent
    data class ShowDetails(val movie: Movie) : MainEvent
}