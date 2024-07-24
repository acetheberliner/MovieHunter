package progetto.cinema.cinestock.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchMovieApi {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private var retrofitService: SearchMovieApiService? = null

    fun getRetrofitService(): SearchMovieApiService {
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            retrofitService = retrofit.create(SearchMovieApiService::class.java)
        }
        return retrofitService!!
    }
}
