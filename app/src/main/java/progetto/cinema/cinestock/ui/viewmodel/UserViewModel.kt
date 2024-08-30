package progetto.cinema.cinestock.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import progetto.cinema.cinestock.data.database.AppDatabase
import progetto.cinema.cinestock.data.entity.GuestSession
import progetto.cinema.cinestock.data.dao.GuestSessionDao
import progetto.cinema.cinestock.models.signIn.GuestSessionResponse
import progetto.cinema.cinestock.models.signIn.TmdbApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val apiKey = "e96d473555668ee67739012c7f140604"
    private val guestSessionDao: GuestSessionDao = AppDatabase.getDatabase(application).guestSessionDao()

    private val tmdbApiService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TmdbApiService::class.java)

    fun login(username: String, password: String, onResult: (Boolean, String?) -> Unit) = viewModelScope.launch {
        try {
            // Check if the user exists in the database
            val existingSession = guestSessionDao.getGuestSessionByUsername(username)

            if (existingSession != null) {
                // If the user exists, verify the password
                if (existingSession.password == password) {
                    onResult(true, null)
                } else {
                    // The password is wrong
                    onResult(false, "Password is incorrect.")
                }
            } else {
                // The user does not exist, create a new session
                val guestSessionResponse: GuestSessionResponse = tmdbApiService.createGuestSession(apiKey)
                val guestSessionId = guestSessionResponse.guest_session_id

                val newSession = GuestSession(username, password, guestSessionId)
                guestSessionDao.insertGuestSession(newSession)

                // Check if the insertion was successful
                val insertedSession = guestSessionDao.getGuestSessionByUsername(username)
                if (insertedSession != null && insertedSession.password == password) {
                    onResult(true, null)
                } else {
                    onResult(false, "Failed to create guest session.")
                }
            }
        } catch (e: Exception) {
            onResult(false, e.message)
        }
    }
}
