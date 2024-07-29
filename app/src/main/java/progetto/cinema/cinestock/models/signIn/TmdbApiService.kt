package progetto.cinema.cinestock.models.signIn

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(@Query("api_key") apiKey: String): GuestSessionResponse
}
