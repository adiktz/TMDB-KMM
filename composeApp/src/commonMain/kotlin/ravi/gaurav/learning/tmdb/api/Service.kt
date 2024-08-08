package ravi.gaurav.learning.tmdb.api

import ravi.gaurav.learning.tmdb.domain.CensoredText
import ravi.gaurav.learning.tmdb.domain.MovieDetails
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

    suspend fun getMovieDetails(movieId: Long): Result<MovieDetails> {
        val url = Constants.movie + "/$movieId"
        val params = mapOf("append_to_response" to "recommendations,credits,external_ids,images,similar,videos,providers")
        return networkManager.get(url, params)
    }
}