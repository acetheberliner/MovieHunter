package progetto.cinema.cinestock.data.sources.remotemodels

import com.squareup.moshi.Json

data class MovieSearchResponseRemoteModel (
    val query: String? = null,
    val page: Int,
    val results: List<FilmsRemoteModel>
)

data class FilmsRemoteModel(
    val adult: Boolean,
    val backdropPath: String?, // set to null to handle potential missing values
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    @Json(name = "description") val overview: String,
    val popularity: Double,
    @Json(name = "movie_image") val posterPath: String,
    val releaseDate: String,
    @Json(name = "movie_title") val title: String,
    val video: Boolean?, // set to null to handle potential missing values
    val voteAverage: Double?, // set to null to handle potential missing values
    val voteCount: Int? // set to null to handle potential missing values
)