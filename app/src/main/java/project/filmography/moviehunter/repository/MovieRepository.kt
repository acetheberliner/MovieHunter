package project.filmography.moviehunter.repository

import android.content.Context
import project.filmography.moviehunter.models.movie.TMDbMovie
import project.filmography.moviehunter.network.movie.MovieApi
import project.filmography.moviehunter.network.search.SearchMovieApi

// Repository per gestire le operazioni sui film
class MovieRepository(private val context: Context) {

    // Ottiene i film di tendenza
    suspend fun getPopularMovies(apiKey: String): List<TMDbMovie> {
        return MovieApi.getRetrofitService().getTrendingMovies(apiKey).results
    }

    // Cerca film in base alla query
    suspend fun searchMoviesByQuery(apiKey: String, query: String): List<TMDbMovie> {
        return SearchMovieApi.getRetrofitService().searchMovies(apiKey, query).results
    }

    // Ottiene i dettagli di un film specifico
    suspend fun getspecificMovieDetails(apiKey: String, movieId: Int): TMDbMovie {
        return MovieApi.getRetrofitService().getMovieDetails(movieId, apiKey)
    }
}
