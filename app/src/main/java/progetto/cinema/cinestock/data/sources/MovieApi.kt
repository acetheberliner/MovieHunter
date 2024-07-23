package progetto.cinema.cinestock.data.sources

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MovieApi {
    private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    const val API_KEY = "e96d473555668ee67739012c7f140604"
    private const val API_HOST = "https://www.themoviedb.org/"


    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit
        .Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    // punto di ingresso pubblico usato per chiamare effettivamente le API
    val rootService: RemoteServiceInterface by lazy { retrofit.create(RemoteServiceInterface::class.java) }

}