package project.filmography.moviehunter.models.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TMDbMovie(
    val id: Int,                // ID univoco del film
    val original_title: String, // Titolo originale del film
    val overview: String,       // Descrizione del film
    val poster_path: String,    // Percorso dell'immagine del poster
    val release_date: String,   // Data di rilascio del film
    val vote_average: Double    // Voto medio del film
) : Parcelable
