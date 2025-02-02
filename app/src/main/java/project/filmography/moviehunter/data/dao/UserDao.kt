package project.filmography.moviehunter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.filmography.moviehunter.data.entity.User

/** DAO per gli utenti. */
@Dao
interface UserDao {

    /** Inserisce un utente. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    /** Inserisce una lista di utenti. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<User>)

    /** Restituisce tutti gli utenti ordinati per nome. */
    @Query("SELECT * FROM user_table ORDER BY name ASC")
    fun getAllUsers(): LiveData<List<User>>

    /** Restituisce il numero totale di utenti. */
    @Query("SELECT COUNT(*) FROM user_table")
    suspend fun getUserCount(): Int
}
