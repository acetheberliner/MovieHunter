package progetto.cinema.cinestock.models.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TMDbMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
) : Parcelable {
    val posterUrl: String
        get() = "https://image.tmdb.org/t/p/w500/$posterPath"
}
