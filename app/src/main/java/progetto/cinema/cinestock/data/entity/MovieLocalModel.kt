package progetto.cinema.cinestock.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "movie")
data class MovieLocalModel (
    @PrimaryKey  val originalTitle: String,
    @ColumnInfo(name = "movie_img") val posterPath: String?,
    @ColumnInfo(name = "movie_popularity") val popularity: Double?,
    @ColumnInfo(name = "release_date") val releaseDate: String?
)
