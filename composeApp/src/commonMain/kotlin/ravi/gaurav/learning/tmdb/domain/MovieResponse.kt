package ravi.gaurav.learning.tmdb.domain

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder

@Serializable
data class Response(
    val page: Long,
    val results: List<Movie>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
)

@Serializable
data class Movie(
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
    @SerialName("en")
    En("en"),
    @SerialName("es")
    Es("es"),
    @SerialName("zh")
    Zh("zh");
}

private fun Movie.Companion.defaultMovie(): Movie {
    val jsonData = "{\n" +
            "      \"adult\": false,\n" +
            "      \"backdrop_path\": \"/9l1eZiJHmhr5jIlthMdJN5WYoff.jpg\",\n" +
            "      \"genre_ids\": [\n" +
            "        28,\n" +
            "        35,\n" +
            "        878\n" +
            "      ],\n" +
            "      \"id\": 533535,\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"original_title\": \"Deadpool & Wolverine\",\n" +
            "      \"overview\": \"A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.\",\n" +
            "      \"popularity\": 23022.328,\n" +
            "      \"poster_path\": \"/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg\",\n" +
            "      \"release_date\": \"2024-07-24\",\n" +
            "      \"title\": \"Deadpool & Wolverine\",\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 7.913,\n" +
            "      \"vote_count\": 1344\n" +
            "    }"

    val movie = Json.decodeFromString<Movie>(jsonData)
    return movie
}

val Movie.Companion.DEFAULT: Movie
    get() = defaultMovie()
