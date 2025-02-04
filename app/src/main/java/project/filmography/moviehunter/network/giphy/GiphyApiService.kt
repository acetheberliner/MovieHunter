package project.filmography.moviehunter.network.giphy

import project.filmography.moviehunter.models.giphy.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Servizio API per le GIF
interface GiphyApiService {

    // Ottiene le GIFs per un dato film
    @GET("gifs/search")
    suspend fun getGifsForMovie(
        @Query("q") query: String,                     // Parola chiave per la ricerca (titolo del film)
        @Query("api_key") apiKey: String,               // Chiave API di Giphy
        @Query("limit") limit: Int = 3                  // Limita il numero di risultati
    ): GiphyResponse
}
