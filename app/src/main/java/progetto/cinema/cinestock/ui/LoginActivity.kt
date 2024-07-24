// LoginActivity.kt
package progetto.cinema.cinestock.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import progetto.cinema.cinestock.MainActivity
import progetto.cinema.cinestock.R
import progetto.cinema.cinestock.MovieDetailActivity

class LoginActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private var selectedMovieId: Int? = null // Variabile per memorizzare l'ID del film selezionato

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewFlipper = findViewById<ViewFlipper>(R.id.view_flipper)

        val loginButton = findViewById<Button>(R.id.login_button)
        val signinButton = findViewById<Button>(R.id.signin_button)
        val loginSubmitButton = findViewById<Button>(R.id.login_submit_button)
        val signinSubmitButton = findViewById<Button>(R.id.signin_submit_button)

        val loginUsernameEditText = findViewById<EditText>(R.id.login_username)
        val loginPasswordEditText = findViewById<EditText>(R.id.login_password)
        val signinUsernameEditText = findViewById<EditText>(R.id.signin_username)
        val signinPasswordEditText = findViewById<EditText>(R.id.signin_password)
        val signinConfirmPasswordEditText = findViewById<EditText>(R.id.signin_confirm_password)

        val loginBackButton = findViewById<Button>(R.id.login_back_button)
        val signinBackButton = findViewById<Button>(R.id.signin_back_button)

        val backToFilmButton = findViewById<Button>(R.id.back_to_film_button)
        val loginBackToFilmButton = findViewById<Button>(R.id.login_back_to_film_button)
        val signinBackToFilmButton = findViewById<Button>(R.id.signin_back_to_film_button)

        loginButton.setOnClickListener {
            viewFlipper.displayedChild = 1 // Mostra i campi di login
        }

        signinButton.setOnClickListener {
            viewFlipper.displayedChild = 2 // Mostra i campi di sign-in
        }

        loginSubmitButton.setOnClickListener {
            val username = loginUsernameEditText.text.toString()
            val password = loginPasswordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                performLogin(username, password)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        signinSubmitButton.setOnClickListener {
            val username = signinUsernameEditText.text.toString()
            val password = signinPasswordEditText.text.toString()
            val confirmPassword = signinConfirmPasswordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                performRegister(username, password)
            } else {
                Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()
            }
        }

        loginBackButton.setOnClickListener {
            viewFlipper.displayedChild = 0 // Mostra il contenitore iniziale dei pulsanti
        }

        signinBackButton.setOnClickListener {
            viewFlipper.displayedChild = 0 // Mostra il contenitore iniziale dei pulsanti
        }

        backToFilmButton.setOnClickListener {
            navigateToMainActivity()
        }

        loginBackToFilmButton.setOnClickListener {
            navigateToMainActivity()
        }

        signinBackToFilmButton.setOnClickListener {
            navigateToMainActivity()
        }

        // Recupera l'ID del film passato
        selectedMovieId = intent.getIntExtra("MOVIE_ID", -1)
    }

    private fun performLogin(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userViewModel.login(username, password)
            runOnUiThread {
                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                navigateToMovieDetailActivity()
            }
        }
    }

    private fun performRegister(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userViewModel.register(username, password)
            runOnUiThread {
                Toast.makeText(this@LoginActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                navigateToMovieDetailActivity()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Chiude la LoginActivity per non tornare ad essa premendo il pulsante "Back"
    }

    private fun navigateToMovieDetailActivity() {
        selectedMovieId?.let { movieId ->
            val intent = Intent(this, MovieDetailActivity::class.java).apply {
                putExtra("MOVIE_ID", movieId) // Passa l'ID del film alla MovieDetailActivity
            }
            startActivity(intent)
            finish() // Chiude la LoginActivity per non tornare ad essa premendo il pulsante "Back"
        }
    }
}
