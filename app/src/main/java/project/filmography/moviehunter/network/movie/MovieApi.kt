package project.filmography.moviehunter.network.movie

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieApi {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private var retrofitService: MovieApiService? = null

    fun getRetrofitService(): MovieApiService {
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            retrofitService = retrofit.create(MovieApiService::class.java)
        }
        return retrofitService!!
    }
}
