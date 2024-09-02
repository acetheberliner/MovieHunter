package progetto.cinema.cinestock.repository

import progetto.cinema.cinestock.data.dao.UserDao
import progetto.cinema.cinestock.data.entity.User

class UserRepository(private val userDao: UserDao) {

    // Inserts a users list
    suspend fun insertAll(users: List<User>) {
        userDao.insertAll(users)
    }
}
