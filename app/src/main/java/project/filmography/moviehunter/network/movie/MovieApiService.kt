package project.filmography.moviehunter.network.movie

import project.filmography.moviehunter.models.movie.TMDbMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "it"
    ): TMDbResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "it"
    ): TMDbMovie
}

data class TMDbResponse(
    val results: List<TMDbMovie>
)
