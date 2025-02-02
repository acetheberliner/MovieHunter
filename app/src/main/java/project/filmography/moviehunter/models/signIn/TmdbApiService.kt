package project.filmography.moviehunter.models.signIn

import retrofit2.http.GET
import retrofit2.http.Query

// Interfaccia per il servizio API di TMDb
interface TmdbApiService {

    // Funzione per creare una sessione ospite
    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(
        @Query("api_key") apiKey: String // Chiave API per autenticazione
    ): GuestSessionResponse
}
