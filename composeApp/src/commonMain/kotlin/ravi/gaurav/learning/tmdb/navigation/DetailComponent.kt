package ravi.gaurav.learning.tmdb.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import ravi.gaurav.learning.tmdb.api.Repository
import ravi.gaurav.learning.tmdb.domain.Movie
import ravi.gaurav.learning.tmdb.domain.MovieDetails
import ravi.gaurav.learning.tmdb.domain.RecommendationsResult
import ravi.gaurav.learning.tmdb.domain.toMovie

class DetailComponent(
    componentContext: ComponentContext,
    val movieId: Long,
    private val repo: Repository = getKoin().get(),
    val onBack:() -> Unit,
    val onMovieSelected: (Long) -> Unit
): ComponentContext by componentContext {

    private var _details:MutableStateFlow<MovieDetails?> = MutableStateFlow(null)
    val details get() = _details.asStateFlow()

    private val scope = coroutineScope(Job() + Dispatchers.IO)

    fun goBack() { onBack() }

    fun getMovieDetails(movieId: Long) {

        scope.launch {
            val response = repo.getMovieDetails(movieId)
            response.onSuccess { details ->
                _details.value = details
            }
            response.onFailure {
                println(it.message)
            }
        }

    }

    fun onMovieSelected(recommendationsResult: RecommendationsResult) {
        recommendationsResult.id?.let { onMovieSelected(it) }
    }
}