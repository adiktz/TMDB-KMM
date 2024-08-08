package ravi.gaurav.learning.tmdb.domain


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
data class MovieDetails (
    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,

    val budget: Long? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Long? = null,

    @SerialName("imdb_id")
    val imdbID: String? = null,

    @SerialName("origin_country")
    val originCountry: List<String>? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    val overview: String? = null,
    val popularity: Double? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>? = null,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    val revenue: Long? = null,
    val runtime: Long? = null,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null,

    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Long? = null,

    val recommendations: Recommendations? = null,
    val credits: Credits? = null,

    @SerialName("external_ids")
    val externalIDS: ExternalIDS? = null,

    val images: Images? = null,
    val similar: Recommendations? = null,
    val videos: Videos? = null
)

@Serializable
data class BelongsToCollection (
    val id: Long? = null,
    val name: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null
)

@Serializable
data class Credits (
    val cast: List<Cast>? = null,
    val crew: List<Cast>? = null
)

@Serializable
data class Cast (
    val adult: Boolean? = null,
    val gender: Long? = null,
    val id: Long? = null,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    val name: String? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    val popularity: Double? = null,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("cast_id")
    val castID: Long? = null,

    val character: String? = null,

    @SerialName("credit_id")
    val creditID: String? = null,

    val order: Long? = null,
    val department: String? = null,
    val job: String? = null
)

@Serializable
data class ExternalIDS (
    @SerialName("imdb_id")
    val imdbID: String? = null,

    @SerialName("wikidata_id")
    val wikidataID: String? = null,

    @SerialName("facebook_id")
    val facebookID: String? = null,

    @SerialName("instagram_id")
    val instagramID: String? = null,

    @SerialName("twitter_id")
    val twitterID: String? = null
)

@Serializable
data class Genre (
    val id: Long? = null,
    val name: String? = null
)

@Serializable
data class Images (
    val backdrops: List<Backdrop>? = null,
    val logos: List<Backdrop>? = null,
    val posters: List<Backdrop>? = null
)

@Serializable
data class Backdrop (
    @SerialName("aspect_ratio")
    val aspectRatio: Double? = null,

    val height: Long? = null,

    @SerialName("iso_639_1")
    val iso639_1: String? = null,

    @SerialName("file_path")
    val filePath: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Long? = null,

    val width: Long? = null
)



@Serializable
data class ProductionCompany (
    val id: Long? = null,

    @SerialName("logo_path")
    val logoPath: String? = null,

    val name: String? = null,

    @SerialName("origin_country")
    val originCountry: String? = null
)

@Serializable
data class ProductionCountry (
    @SerialName("iso_3166_1")
    val iso3166_1: String? = null,

    val name: String? = null
)

@Serializable
data class Recommendations (
    val page: Long? = null,
    val results: List<RecommendationsResult>? = null,

    @SerialName("total_pages")
    val totalPages: Long? = null,

    @SerialName("total_results")
    val totalResults: Long? = null
)

@Serializable
data class RecommendationsResult (
    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    val id: Long? = null,
    val title: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    val overview: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("media_type")
    val mediaType: MediaType? = null,

    val adult: Boolean? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>? = null,

    val popularity: Double? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    val video: Boolean? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Long? = null
)

@Serializable
enum class MediaType(val value: String) {
    @SerialName("movie") Movie("movie");
}

@Serializable
data class SpokenLanguage (
    @SerialName("english_name")
    val englishName: String? = null,

    @SerialName("iso_639_1")
    val iso639_1: String? = null,

    val name: String? = null
)

@Serializable
data class Videos (
    val results: List<VideosResult>? = null
)

@Serializable
data class VideosResult (
    @SerialName("iso_639_1")
    val iso639_1: String? = null,

    @SerialName("iso_3166_1")
    val iso3166_1: String? = null,

    val name: String? = null,
    val key: String? = null,
    val site: Site? = null,
    val size: Long? = null,
    val type: String? = null,
    val official: Boolean? = null,

    @SerialName("published_at")
    val publishedAt: String? = null,

    val id: String? = null
)

@Serializable
enum class Site(val value: String) {
    @SerialName("YouTube") YouTube("YouTube");
}

fun RecommendationsResult.toMovie(): Movie {
    val jsonValue = Json.encodeToJsonElement(this)
    val movie = Json.decodeFromJsonElement<Movie>(jsonValue)
    return movie
}