package project.filmography.moviehunter.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.filmography.moviehunter.data.entity.GuestSession

/** DAO per le sessioni guest. */
@Dao
interface GuestSessionDao {

    /** Recupera una sessione guest. */
    @Query("SELECT * FROM guest_session_table WHERE username = :username LIMIT 1")
    suspend fun getGuestSessionByUsername(username: String): GuestSession?

    /** Inserisce o aggiorna una sessione guest. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuestSession(guestSession: GuestSession)
}
