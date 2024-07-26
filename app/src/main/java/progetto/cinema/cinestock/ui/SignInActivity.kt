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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import progetto.cinema.cinestock.MainActivity
import progetto.cinema.cinestock.R

class SignInActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private var selectedMovieId: Int? = null // variable that stores the ID of the selected film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val viewFlipper = findViewById<ViewFlipper>(R.id.view_flipper)

        val signinButton = findViewById<Button>(R.id.signin_button)
        val signinSubmitButton = findViewById<Button>(R.id.signin_submit_button)

        val UsernameEditText = findViewById<EditText>(R.id.sign_username)
        val PasswordEditText = findViewById<EditText>(R.id.signin_password)

        val signinBackButton = findViewById<Button>(R.id.signin_back_button)

        val backToFilmButton = findViewById<Button>(R.id.back_to_film_button)
        val signinBackToFilmButton = findViewById<Button>(R.id.signin_back_to_film_button)


        signinButton.setOnClickListener {
            viewFlipper.displayedChild = 1 // shows input fields
        }

        signinSubmitButton.setOnClickListener {
            val username = UsernameEditText.text.toString()
            val password = PasswordEditText.text.toString()

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
        Log.d("LoginActivity", "Received movie ID: $selectedMovieId")

        if (selectedMovieId == -1) {
            Toast.makeText(this, "No movie ID provided", Toast.LENGTH_SHORT).show()
            // Opt: Redirect to some default or error page
        } else{
            Log.d("LoginActivity", "Movie ID is valid: $selectedMovieId")
        }
    }

    private fun performLogin(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                userViewModel.login(username, password)
                runOnUiThread {
                    Toast.makeText(this@SignInActivity, "Sign In Successful", Toast.LENGTH_SHORT).show()
                    navigateToRiepilogoActivity()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@SignInActivity, "Sign In Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // closes LoginActivity by clicking the back button
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
