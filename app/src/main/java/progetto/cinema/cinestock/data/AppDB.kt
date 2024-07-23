package progetto.cinema.cinestock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import progetto.cinema.cinestock.data.dao.MovieDao
import progetto.cinema.cinestock.data.entity.MovieLocalModel

@Database(
    entities = [MovieLocalModel::class],
    version = 1,
    exportSchema = false
)

abstract class AppDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase){
            super.onCreate(db)
        }
    }

    companion object {
        @Volatile
        private var Instance: AppDB? = null

        fun getDB(
            context: Context,
            scope: CoroutineScope
        ):AppDB {
            return Instance ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "CineStock_database"
                ).addCallback(DatabaseCallback(scope)).build()
                Instance = instance
                instance
            }
        }
    }
}