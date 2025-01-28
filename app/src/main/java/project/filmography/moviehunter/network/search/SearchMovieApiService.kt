package project.filmography.moviehunter.network.search

import project.filmography.moviehunter.models.movie.TMDbMovie
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchMovieApiService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): SearchMovieResponse
}

data class SearchMovieResponse(
    val results: List<TMDbMovie>
)
