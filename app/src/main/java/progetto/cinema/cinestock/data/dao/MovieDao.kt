package progetto.cinema.cinestock.data.dao

import androidx.room.Dao
import androidx.room.Query
import progetto.cinema.cinestock.data.entity.MovieLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieLocalModel>>

    @Query("SELECT * FROM movie WHERE originalTitle = :originalTitle")
    suspend fun getMovieByOriginalTitle(originalTitle: String): MovieLocalModel?

//
//    @Insert
//    suspend fun insert(movie: Movie)
//
//    @Query("DELETE FROM movie")
//    suspend fun deleteAll()
//
//    @Insert
//    suspend fun insertAll(movies: List<Movie>)
//
//    @Query("SELECT * FROM movie WHERE id = :id")
//    fun getMovieById(id: Int): LiveData<Movie>
}
