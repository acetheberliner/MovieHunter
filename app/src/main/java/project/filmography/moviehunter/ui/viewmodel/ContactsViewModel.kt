package project.filmography.moviehunter.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.filmography.moviehunter.data.entity.User
import project.filmography.moviehunter.repository.UserRepository
import project.filmography.moviehunter.data.database.UserDatabase

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application, viewModelScope).userDao()
        repository = UserRepository(userDao)
    }

    fun insertAll(users: List<User>) = viewModelScope.launch {
        repository.insertAll(users)
    }
}
