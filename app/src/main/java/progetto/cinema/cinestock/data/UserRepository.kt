package progetto.cinema.cinestock.data

import androidx.lifecycle.LiveData
import progetto.cinema.cinestock.data.dao.UserDao
import progetto.cinema.cinestock.data.entity.User

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    // Inserts a users list
    suspend fun insertAll(users: List<User>) {
        userDao.insertAll(users)
    }
}
