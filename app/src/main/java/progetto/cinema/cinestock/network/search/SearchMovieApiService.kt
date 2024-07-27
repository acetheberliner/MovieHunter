package progetto.cinema.cinestock.network.search

import progetto.cinema.cinestock.models.movie.TMDbMovie
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
