package ravi.gaurav.learning.tmdb.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class Repository(
    private val service: Service
) {

    suspend fun getPopularMovies(pageNumber: Long = 1) = service.getPopularMovies(pageNumber)
}