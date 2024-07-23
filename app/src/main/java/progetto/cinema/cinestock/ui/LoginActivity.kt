package progetto.cinema.cinestock.ui

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
import progetto.cinema.cinestock.R

class LoginActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

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

        loginButton.setOnClickListener {
            viewFlipper.displayedChild = 1 // Show login fields
        }

        signinButton.setOnClickListener {
            viewFlipper.displayedChild = 2 // Show sign-in fields
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
            viewFlipper.displayedChild = 0 // Show the initial buttons container
        }

        signinBackButton.setOnClickListener {
            viewFlipper.displayedChild = 0 // Show the initial buttons container
        }
    }

    private fun performLogin(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userViewModel.login(username, password)
            runOnUiThread {
                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                // Remove the redirection to SuccessActivity
            }
        }
    }

    private fun performRegister(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userViewModel.register(username, password)
            runOnUiThread {
                Toast.makeText(this@LoginActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                // Remove the redirection to SuccessActivity
            }
        }
    }
}
