package ravi.gaurav.learning.tmdb.api

import ravi.gaurav.learning.tmdb.domain.CensoredText
import ravi.gaurav.learning.tmdb.domain.Response
import ravi.gaurav.learning.tmdb.networking.NetworkManager
import ravi.gaurav.learning.tmdb.util.Constants

class Service(
    private val networkManager: NetworkManager
) {

    suspend fun getPopularMovies(pageNumber: Long): Result<Response> {
        val url = Constants.movie + Constants.popular
        return networkManager.get(url, mapOf("page" to pageNumber))
    }
}