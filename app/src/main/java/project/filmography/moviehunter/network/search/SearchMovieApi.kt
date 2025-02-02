package project.filmography.moviehunter.network.search

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Gestione della connessione e delle chiamate API per la ricerca di film
object SearchMovieApi {
    private const val BASE_URL = "https://api.themoviedb.org/3/" // URL di base dell'API
    private var retrofitService: SearchMovieApiService? = null // Servizio API null inizialmente

    // Servizio Retrofit per la ricerca di film
    fun getRetrofitService(): SearchMovieApiService {
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()) // Converte la risposta JSON tramite Gson
                .baseUrl(BASE_URL) // Imposta l'URL di base
                .build()
            retrofitService = retrofit.create(SearchMovieApiService::class.java) // Crea il servizio API
        }
        return retrofitService!! // Restituisce il servizio API creato
    }
}
