package progetto.cinema.cinestock.network

import progetto.cinema.cinestock.models.movie.TMDbMovie
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ): TMDbResponse
}

data class TMDbResponse(
    val results: List<TMDbMovie>
)
