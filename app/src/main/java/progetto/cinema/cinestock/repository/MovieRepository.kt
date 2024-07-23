package progetto.cinema.cinestock.repository

import android.content.Context
import progetto.cinema.cinestock.models.movie.TMDbMovie
import progetto.cinema.cinestock.network.MovieApi

class MovieRepository(private val context: Context) {
    suspend fun getTrendingMovies(apiKey: String): List<TMDbMovie> {
        return MovieApi.getRetrofitService().getTrendingMovies(apiKey).results
    }
}
