package progetto.cinema.cinestock.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): LiveData<List<Movie>>

    @Insert
    suspend fun insert(movie: Movie)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id: Int): LiveData<Movie>
}
