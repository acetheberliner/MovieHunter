package project.filmography.moviehunter.repository

import project.filmography.moviehunter.data.dao.UserDao
import project.filmography.moviehunter.data.entity.User

class UserRepository(private val userDao: UserDao) {

    // Inserts a users list
    suspend fun insertAll(users: List<User>) {
        userDao.insertAll(users)
    }
}
