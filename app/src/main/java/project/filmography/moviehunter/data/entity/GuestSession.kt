package project.filmography.moviehunter.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Entità per la sessione guest. */
@Entity(tableName = "guest_session_table")
data class GuestSession(
    @PrimaryKey val username: String,
    val password: String,
    val guestSessionId: String
)
