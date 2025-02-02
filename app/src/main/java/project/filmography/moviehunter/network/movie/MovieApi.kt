package project.filmography.moviehunter.network.movie

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Gestione della connessione e delle chiamate API relative ai film
object MovieApi {
    private const val BASE_URL = "https://api.themoviedb.org/3/" // URL di base dell'API
    private var retrofitService: MovieApiService? = null // Servizio API inizialmente null

    // Servizio Retrofit
    fun getRetrofitService(): MovieApiService {
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()) // Gson per convertire le risposte JSON
                .baseUrl(BASE_URL)
                .build()
            retrofitService = retrofit.create(MovieApiService::class.java) // Crea il servizio API
        }
        return retrofitService!! // Restituisce il servizio API creato
    }
}
