package progetto.cinema.cinestock.repository

import androidx.annotation.WorkerThread
import progetto.cinema.cinestock.data.dao.MovieDao
import progetto.cinema.cinestock.data.entity.MovieLocalModel
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    // list of films as flow

    val allMovies: Flow<List<MovieLocalModel>> = movieDao.getAllMovies()

    //@WorkerThread
//    suspend fun insert(movie: Movie) {
//        movieDao.insert(movie)
//    }
//
//    suspend fun insertAll(movies: List<Movie>) {
//        movieDao.insertAll(movies)
//    }
//
//    suspend fun refreshMovies() {
//        // Supponendo che ci sia un'API per ottenere film, MovieApi.getRetrofitService(context) è un esempio
//        val movies = MovieApi.getRetrofitService(context).getMovies()
//        insertAll(movies)
//    }
//
//    fun getMovieById(id: Int): LiveData<Movie> {
//        return movieDao.getMovieById(id)
//    }

    @WorkerThread
    suspend fun getMovieByOriginalTitle(originalTitle: String): MovieLocalModel? {
        return movieDao.getMovieByOriginalTitle(originalTitle)
    }
}



