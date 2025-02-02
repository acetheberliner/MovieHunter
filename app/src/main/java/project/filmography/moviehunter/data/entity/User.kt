package project.filmography.moviehunter.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/** Entità utente con nome e numero di telefono. */
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String, // Nome e cognome
    val phoneNumber: String
) : Serializable
