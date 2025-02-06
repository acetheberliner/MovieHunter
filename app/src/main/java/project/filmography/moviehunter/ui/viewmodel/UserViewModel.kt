package project.filmography.moviehunter.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.filmography.moviehunter.data.database.AppDatabase
import project.filmography.moviehunter.data.entity.GuestSession
import project.filmography.moviehunter.data.dao.GuestSessionDao
import project.filmography.moviehunter.models.login.GuestSessionResponse
import project.filmography.moviehunter.models.login.TmdbApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ViewModel per la gestione della sessione utente
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val apiKey = "854938d42be4125ce84812f957f4f8b5"  // Chiave API per la TMDb
    private val guestSessionDao: GuestSessionDao = AppDatabase.getDatabase(application).guestSessionDao()

    // Servizio API per la creazione della sessione ospite
    private val tmdbApiService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")  // URL base per le richieste
        .addConverterFactory(GsonConverterFactory.create())  // Converte la risposta JSON in oggetti Kotlin
        .build()
        .create(TmdbApiService::class.java)

    // Funzione per effettuare il login, sia se l'utente esiste o deve essere creato
    fun login(username: String, password: String, onResult: (Boolean, String?) -> Unit) = viewModelScope.launch {
        try {
            // Verifica se l'utente esiste già nel database
            val existingSession = guestSessionDao.getGuestSessionByUsername(username)

            if (existingSession != null) {
                // Se l'utente esiste, verifica la password
                if (existingSession.password == password) {
                    onResult(true, null)  // Login riuscito
                } else {
                    // La password è errata
                    onResult(false, "Password errata")
                }
            } else {
                // Se l'utente non esiste, crea una nuova sessione ospite
                val guestSessionResponse: GuestSessionResponse = tmdbApiService.createGuestSession(apiKey)
                val guestSessionId = guestSessionResponse.guest_session_id

                // Crea una nuova sessione per l'utente
                val newSession = GuestSession(username, password, guestSessionId)
                guestSessionDao.insertGuestSession(newSession)

                // Verifica se la sessione è stata inserita correttamente
                val insertedSession = guestSessionDao.getGuestSessionByUsername(username)
                if (insertedSession != null && insertedSession.password == password) {
                    onResult(true, null)  // Login riuscito
                } else {
                    onResult(false, "Failed to create guest session.")  // Errore creazione sessione
                }
            }
        } catch (e: Exception) {
            // Gestione degli errori
            onResult(false, e.message)
        }
    }
}
