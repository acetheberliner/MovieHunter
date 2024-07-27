package progetto.cinema.cinestock.data

import androidx.lifecycle.LiveData
import progetto.cinema.cinestock.models.user.User

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }
}
