package progetto.cinema.cinestock.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmModel (
    val identifier: String,
    val name: String,
//    val description: String,
//    val address: String,
    val pictureUrl: String,
//    val isFavorite: Boolean
) : Parcelable