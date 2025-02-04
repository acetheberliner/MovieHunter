package project.filmography.moviehunter.network.giphy

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Gestione della connessione e delle chiamate API relative alle GIF
object GiphyApi {
    private const val BASE_URL = "https://api.giphy.com/v1/" // URL di base dell'API
    private var retrofitService: GiphyApiService? = null // Servizio API inizialmente null

    // Servizio Retrofit
    fun getRetrofitService(): GiphyApiService {
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()) // Gson per convertire le risposte JSON
                .baseUrl(BASE_URL)
                .build()
            retrofitService = retrofit.create(GiphyApiService::class.java) // Crea il servizio API
        }
        return retrofitService!! // Restituisce il servizio API creato
    }
}
