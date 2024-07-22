package progetto.cinema.cinestock.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object FilmApi {
    private const val BASE_URL =
        "https://online-movie-database.p.rapidapi.com/v2/search?searchTerm=Forrest%20Gump&type=MOVIE&first=5&country=IT&language=it"
    private const val API_KEY = "457d948840mshf11db9739d21154p1a4762jsne69a20695a47"
    private const val API_HOST = "online-movie-database.p.rapidapi.com"


    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    // punto di ingresso pubblico usato per chiamare effettivamente le API
    val rootService: RootRemoteServiceInterface by lazy { retrofit.create(RootRemoteServiceInterface::class.java) }

}