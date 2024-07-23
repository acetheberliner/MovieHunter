package progetto.cinema.cinestock.ui.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * Represent how the films in the search are saved
 */

@Parcelize
data class Movie(
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    @Json(name = "description") val overview: String,
    val popularity: Double,
    @Json(name = "movie_image") val posterPath: String,
    val releaseDate: String,
    @Json(name = "movie_title") val title: String,
    val voteCount: Int?
) : Parcelable