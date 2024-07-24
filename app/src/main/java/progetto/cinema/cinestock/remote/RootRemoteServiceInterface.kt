package progetto.cinema.cinestock.remote

import progetto.cinema.cinestock.remote.remotemodels.search.FilmSearchResponseRemoteModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RootRemoteServiceInterface {

    @Headers(
        "X-RapidAPI-Key: 457d948840mshf11db9739d21154p1a4762jsne69a20695a47",
        "X-RapidAPI-Host: online-movie-database.p.rapidapi.com"
    )
    @GET("/v2/search")
    fun filmSearch(
        @Query("searchTerm") title: String,  // Nome del parametro per il termine di ricerca
        @Query("type") type: String? = null,  // Tipo di contenuto da cercare (NAME, MOVIE)
        @Query("first") first: Int? = null,  // Numero di elementi per risposta per il paging
        @Query("country") country: String? = null,  // Codice del paese (opzionale)
        @Query("language") language: String? = null // Lingua (opzionale)
    ): FilmSearchResponseRemoteModel


}