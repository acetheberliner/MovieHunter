package project.filmography.moviehunter.repository

import project.filmography.moviehunter.data.dao.UserDao
import project.filmography.moviehunter.data.entity.User

// Repository per gestire le operazioni sugli utenti
class UserRepository(private val userDao: UserDao) {

    // Inserisce una lista di utenti
    suspend fun insertAll(users: List<User>) {
        userDao.insertAll(users)
    }
}
