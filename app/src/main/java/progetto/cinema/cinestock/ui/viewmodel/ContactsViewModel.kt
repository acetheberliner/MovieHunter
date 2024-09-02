package progetto.cinema.cinestock.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import progetto.cinema.cinestock.data.entity.User
import progetto.cinema.cinestock.repository.UserRepository
import progetto.cinema.cinestock.data.database.UserDatabase

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
