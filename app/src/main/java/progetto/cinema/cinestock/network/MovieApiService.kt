package progetto.cinema.cinestock.network

import android.content.Context
import progetto.cinema.cinestock.R
import progetto.cinema.cinestock.data.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MovieApiService {
    @GET("movies")
    suspend fun getMovies(): List<Movie>
}

object MovieApi {
    private var retrofitService: MovieApiService? = null

    fun getRetrofitService(context: Context): MovieApiService {
        if (retrofitService == null) {
            val baseUrl = context.getString(R.string.api_base_url)
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
            retrofitService = retrofit.create(MovieApiService::class.java)
        }
        return retrofitService!!
    }
}
