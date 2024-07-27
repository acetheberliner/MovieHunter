package progetto.cinema.cinestock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import progetto.cinema.cinestock.models.user.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(UserDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class UserDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.userDao())
                    }
                }
            }

            suspend fun populateDatabase(userDao: UserDao) {
                // Check if the database is empty before populating it
                val count = userDao.getUserCount()
                if (count == 0) {
                    // Only add users if the database is empty
                    userDao.insert(User(firstName = "John", lastName = "White", phoneNumber = "123456789"))
                    userDao.insert(User(firstName = "Erik", lastName = "Doe", phoneNumber = "987654321"))
                    userDao.insert(User(firstName = "Alice", lastName = "Smith", phoneNumber = "159732547"))
                    userDao.insert(User(firstName = "Bob", lastName = "Johnson", phoneNumber = "253019873"))
                    userDao.insert(User(firstName = "Carol", lastName = "Williams", phoneNumber = "786320144"))
                    userDao.insert(User(firstName = "Dave", lastName = "Brown", phoneNumber = "786633210"))
                    userDao.insert(User(firstName = "Eve", lastName = "Jones", phoneNumber = "896531046"))
                    userDao.insert(User(firstName = "Frank", lastName = "Miller", phoneNumber = "259751064"))
                    userDao.insert(User(firstName = "Grace", lastName = "Wilson", phoneNumber = "896322017"))
                    userDao.insert(User(firstName = "Hank", lastName = "Moore", phoneNumber = "259800450"))

                }
            }
        }
    }
}
