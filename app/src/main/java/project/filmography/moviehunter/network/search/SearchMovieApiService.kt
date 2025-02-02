package project.filmography.moviehunter.network.search

import project.filmography.moviehunter.models.movie.TMDbMovie
import retrofit2.http.GET
import retrofit2.http.Query

// Servizio API per la ricerca dei film
interface SearchMovieApiService {
    // Funzione per cercare i film
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,   // Chiave API
        @Query("query") query: String,      // Query di ricerca
        @Query("page") page: Int = 1        // Numero della pagina dei risultati
    ): SearchMovieResponse
}

// Risposta per la ricerca dei film
data class SearchMovieResponse(
    val results: List<TMDbMovie> // Lista dei film trovati
)
