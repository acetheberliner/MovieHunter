package progetto.cinema.cinestock.models.login

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TmdbApiService {

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(@Query("api_key") apiKey: String): GuestSessionResponse

    @GET("authentication/token/new")
    suspend fun createRequestToken(@Query("api_key") apiKey: String): TokenResponse

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String,
        @Query("request_token") requestToken: String
    ): SessionResponse
}
