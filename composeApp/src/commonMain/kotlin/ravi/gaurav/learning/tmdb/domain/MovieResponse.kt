package ravi.gaurav.learning.tmdb.domain

import kotlinx.serialization.*

@Serializable
data class Response (
    val page: Long,
    val results: List<Movie>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
)

@Serializable
data class Movie (
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String = "",

    @SerialName("genre_ids")
    val genreIDS: List<Long>,

    val id: Long,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String = "",

    @SerialName("release_date")
    val releaseDate: String,

    val title: String,
    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Long
)

@Serializable
enum class OriginalLanguage(val value: String) {
    @SerialName("en") En("en"),
    @SerialName("es") Es("es"),
    @SerialName("zh") Zh("zh");
}
