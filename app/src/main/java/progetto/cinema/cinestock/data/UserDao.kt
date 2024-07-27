package progetto.cinema.cinestock.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import progetto.cinema.cinestock.models.user.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user_table ORDER BY lastName ASC")
    fun getAllUsers(): LiveData<List<User>>
}
