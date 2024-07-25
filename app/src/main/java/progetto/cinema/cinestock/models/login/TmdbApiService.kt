package progetto.cinema.cinestock.models.login

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TmdbApiService {

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(@Query("api_key") apiKey: String): GuestSessionResponse

    @GET("authentication/token/new")
    suspend fun createRequestToken(@Query("api_key") apiKey: String): TokenResponse

}
