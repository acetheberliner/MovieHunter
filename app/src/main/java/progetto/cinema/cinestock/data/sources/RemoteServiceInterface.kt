package progetto.cinema.cinestock.data.sources

import progetto.cinema.cinestock.data.sources.remotemodels.MovieSearchResponseRemoteModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path


interface RemoteServiceInterface {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") originalTitle: String, // Search query (movie title)
        @Query("language") language: String = "en-US", // Set desired language
        @Query("key") apiKey: String = MovieApi.API_KEY
    ): MovieSearchResponseRemoteModel
}

