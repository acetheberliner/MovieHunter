package progetto.cinema.cinestock.ui

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
import progetto.cinema.cinestock.MainActivity
import progetto.cinema.cinestock.R
import progetto.cinema.cinestock.ui.viewmodel.UserViewModel

class SignInActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private var selectedMovieId: Int? = null // variable that stores the ID of the selected film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val viewFlipper = findViewById<ViewFlipper>(R.id.view_flipper)

        val signinButton = findViewById<Button>(R.id.signin_button)
        val signinSubmitButton = findViewById<Button>(R.id.signin_submit_button)

        val usernameEditText = findViewById<EditText>(R.id.sign_username)
        val passwordEditText = findViewById<TextInputEditText>(R.id.signin_password)

        val signinBackButton = findViewById<Button>(R.id.signin_back_button)
        val backToFilmButton = findViewById<Button>(R.id.back_to_homepage_button)
        val signinBackToFilmButton = findViewById<Button>(R.id.signin_back_to_homepage_button)

        signinButton.setOnClickListener {
            viewFlipper.displayedChild = 1 // shows input fields
        }

        signinSubmitButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                performLogin(username, password)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        signinBackButton.setOnClickListener {
            viewFlipper.displayedChild = 0 // shows the initial container of the buttons
        }

        backToFilmButton.setOnClickListener {
            navigateToMainActivity()
        }

        signinBackToFilmButton.setOnClickListener {
            navigateToMainActivity()
        }

        // Retrieve given filmID
        selectedMovieId = intent.getIntExtra("MOVIE_ID", -1)
        Log.d("SigninActivity", "Received movie ID: $selectedMovieId")

        if (selectedMovieId == -1) {
            Toast.makeText(this, "No movie ID provided", Toast.LENGTH_SHORT).show()

        } else {
            Log.d("SigninActivity", "Movie ID is valid: $selectedMovieId")
        }
    }

    private fun performLogin(username: String, password: String) {
        userViewModel.login(username, password) { success, errorMessage ->
            runOnUiThread {
                if (success) {
                    Toast.makeText(this, "Sign In Successful", Toast.LENGTH_SHORT).show()
                    navigateToRiepilogoActivity()
                } else {
                    Toast.makeText(this, "Sign In Failed: $errorMessage", Toast.LENGTH_SHORT).show()
                    // Reset password field to allow re-entry
                    findViewById<TextInputEditText>(R.id.signin_password).text?.clear()
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // closes SigninActivity by clicking the back button
    }

    private fun navigateToRiepilogoActivity() {
        val movieId = selectedMovieId
        if (movieId != null && movieId != -1) {
            val intent = Intent(this, SummaryActivity::class.java).apply {
                putExtra("MOVIE_ID", movieId)
            }
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "No movie ID to navigate", Toast.LENGTH_SHORT).show()
            navigateToMainActivity() // redirect if there is no movie ID
        }
    }
}
