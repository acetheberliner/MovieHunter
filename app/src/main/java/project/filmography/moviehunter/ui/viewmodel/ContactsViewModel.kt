package project.filmography.moviehunter.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.filmography.moviehunter.data.entity.User
import project.filmography.moviehunter.repository.UserRepository
import project.filmography.moviehunter.data.database.UserDatabase

// ViewModel per gestire i dati degli utenti
class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    // Repository per l'accesso ai dati degli utenti
    private val repository: UserRepository

    // Inizializzazione del ViewModel
    init {
        // Ottiene l'istanza del database UserDatabase e il DAO necessario
        val userDao = UserDatabase.getDatabase(application, viewModelScope).userDao()
        repository = UserRepository(userDao)  // Inizializza il repository con il DAO
    }

    // Funzione per inserire una lista di utenti nel database
    fun insertAll(users: List<User>) = viewModelScope.launch {
        repository.insertAll(users)  // Esegue l'inserimento in background
    }
}
