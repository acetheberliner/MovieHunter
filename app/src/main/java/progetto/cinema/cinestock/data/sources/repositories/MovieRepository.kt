package progetto.cinema.cinestock.data.sources.repositories

import progetto.cinema.cinestock.ui.model.Movie

interface MovieRepository {
    suspend fun searchMovies(query: String): Movie
}