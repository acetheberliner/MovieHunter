package project.filmography.moviehunter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import project.filmography.moviehunter.data.entity.GuestSession
import project.filmography.moviehunter.data.dao.GuestSessionDao

/** Database Room principale. */
@Database(entities = [GuestSession::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    /** DAO per le sessioni guest. */
    abstract fun guestSessionDao(): GuestSessionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /** Restituisce un'istanza del database. */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "guest_session_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
