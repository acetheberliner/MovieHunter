package project.filmography.moviehunter.network.movie

import project.filmography.moviehunter.models.movie.TMDbMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Servizio API per i film
interface MovieApiService {

    // Ottiene i film di tendenza della settimana
    @GET("movie/popular")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String,               // Chiave API
        @Query("language") language: String = "it"      // Lingua della risposta
    ): TMDbResponse

    // Ottiene i dettagli di un film
    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,                       // ID del film
        @Query("api_key") apiKey: String,               // Chiave API
        @Query("language") language: String = "it"      // Lingua della risposta
    ): TMDbMovie
}

// Risposta per i film di tendenza
data class TMDbResponse(
    val results: List<TMDbMovie> // Lista dei film
)
