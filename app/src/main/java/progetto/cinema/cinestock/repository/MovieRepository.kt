package progetto.cinema.cinestock.repository

import android.content.Context
import androidx.lifecycle.LiveData
import progetto.cinema.cinestock.data.Movie
import progetto.cinema.cinestock.data.MovieDao
import progetto.cinema.cinestock.network.MovieApi

class MovieRepository(private val movieDao: MovieDao, private val context: Context) {
    val allMovies: LiveData<List<Movie>> = movieDao.getAllMovies()

    suspend fun insert(movie: Movie) {
        movieDao.insert(movie)
    }

    suspend fun insertAll(movies: List<Movie>) {
        movieDao.insertAll(movies)
    }

    suspend fun refreshMovies() {
        // Supponendo che ci sia un'API per ottenere film, MovieApi.getRetrofitService(context) è un esempio
        val movies = MovieApi.getRetrofitService(context).getMovies()
        insertAll(movies)
    }

    fun getMovieById(id: Int): LiveData<Movie> {
        return movieDao.getMovieById(id)
    }
}



