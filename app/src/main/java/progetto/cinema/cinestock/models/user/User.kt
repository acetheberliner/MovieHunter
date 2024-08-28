package progetto.cinema.cinestock.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String, // it includes first and last name
    val phoneNumber: String
) : Serializable
