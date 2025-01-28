package project.filmography.moviehunter.repository

import android.content.Context
import project.filmography.moviehunter.models.movie.TMDbMovie
import project.filmography.moviehunter.network.movie.MovieApi
import project.filmography.moviehunter.network.search.SearchMovieApi

class MovieRepository(private val context: Context) {

    suspend fun getTrendingMovies(apiKey: String): List<TMDbMovie> {
        return MovieApi.getRetrofitService().getTrendingMovies(apiKey).results
    }

    suspend fun searchMovies(apiKey: String, query: String): List<TMDbMovie> {
        return SearchMovieApi.getRetrofitService().searchMovies(apiKey, query).results
    }

    suspend fun getMovieDetails(apiKey: String, movieId: Int): TMDbMovie {
        return MovieApi.getRetrofitService().getMovieDetails(movieId, apiKey)
    }
}
