package project.filmography.moviehunter.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import project.filmography.moviehunter.MainActivity
import project.filmography.moviehunter.R
import project.filmography.moviehunter.ui.viewmodel.UserViewModel

class SignInActivity : AppCompatActivity() {

    // ViewModel per gestire la logica del login
    private val userViewModel: UserViewModel by viewModels()
    private var selectedMovieId: Int? = null  // Variabile per memorizzare l'ID del film selezionato

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Trova gli elementi del layout
        val viewFlipper = findViewById<ViewFlipper>(R.id.view_flipper)
        val signinButton = findViewById<Button>(R.id.signin_button)
        val signinSubmitButton = findViewById<Button>(R.id.signin_submit_button)
        val usernameEditText = findViewById<EditText>(R.id.sign_username)
        val passwordEditText = findViewById<TextInputEditText>(R.id.signin_password)
        val signinBackButton = findViewById<Button>(R.id.signin_back_button)
        val backToFilmButton = findViewById<Button>(R.id.back_to_homepage_button)
        val signinBackToFilmButton = findViewById<Button>(R.id.signin_back_to_homepage_button)

        // Gestisci il click per passare alla seconda schermata
        signinButton.setOnClickListener {
            viewFlipper.displayedChild = 1
        }

        // Gestisci il click per il login
        signinSubmitButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Verifica che username e password non siano vuoti
            if (username.isNotEmpty() && password.isNotEmpty()) {
                performLogin(username, password)  // Chiamata per effettuare il login
            } else {
                Toast.makeText(this, "Username e/o password mancanti", Toast.LENGTH_SHORT).show()
            }
        }

        // Gestisci il click per tornare alla schermata iniziale di login
        signinBackButton.setOnClickListener {
            viewFlipper.displayedChild = 0
        }

        // Gestisci il click per tornare alla homepage
        backToFilmButton.setOnClickListener {
            navigateToMainActivity()  // Naviga alla homepage
        }

        // Gestisci il click per tornare alla homepage dalla schermata di login
        signinBackToFilmButton.setOnClickListener {
            navigateToMainActivity()
        }

        // Ottieni l'ID del film passato tramite Intent
        selectedMovieId = intent.getIntExtra("MOVIE_ID", -1)
        Log.d("SigninActivity", "Received movie ID: $selectedMovieId")

        if (selectedMovieId == -1) {
            Toast.makeText(this, "Nessun film selezionato", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("SigninActivity", "Movie ID is: $selectedMovieId")
        }
    }

    // Funzione per effettuare il login
    private fun performLogin(username: String, password: String) {
        userViewModel.login(username, password) { success, errorMessage ->
            runOnUiThread {
                if (success) {
                    Toast.makeText(this, "Accesso eseguito", Toast.LENGTH_SHORT).show()
                    navigateToRiepilogoActivity()  // Naviga alla schermata di riepilogo
                } else {
                    Toast.makeText(this, "Accesso non riuscito: $errorMessage", Toast.LENGTH_SHORT).show()
                    findViewById<TextInputEditText>(R.id.signin_password).text?.clear()  // Pulisce la password
                }
            }
        }
    }

    // Funzione per navigare alla homepage
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Termina questa activity
    }

    // Funzione per navigare alla schermata di riepilogo (SummaryActivity)
    private fun navigateToRiepilogoActivity() {
        val movieId = selectedMovieId
        if (movieId != null && movieId != -1) {
            val intent = Intent(this, SummaryActivity::class.java).apply {
                putExtra("MOVIE_ID", movieId)  // Passa l'ID del film alla schermata successiva
            }
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Nessun ID film selezionato", Toast.LENGTH_SHORT).show()
            navigateToMainActivity()  // Torna alla homepage se nessun film è selezionato
        }
    }
}
