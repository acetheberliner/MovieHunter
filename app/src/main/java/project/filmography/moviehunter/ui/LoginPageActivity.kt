package project.filmography.moviehunter.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import project.filmography.moviehunter.MainActivity
import project.filmography.moviehunter.R
import project.filmography.moviehunter.ui.viewmodel.UserViewModel
import android.Manifest
import java.io.ByteArrayOutputStream

class LoginPageActivity : AppCompatActivity() {

    // ViewModel per gestire la logica del login
    private val userViewModel: UserViewModel by viewModels()
    private var selectedMovieId: Int? = null  // Variabile per memorizzare l'ID del film selezionato

    private val REQUEST_CAMERA_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 101
    private lateinit var cameraButton: Button
    private lateinit var capturedImageView: ImageView
    private var userPhoto: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // Trova gli elementi del layout
        val viewFlipper = findViewById<ViewFlipper>(R.id.view_flipper)
        val loginButton = findViewById<Button>(R.id.signin_button)
        val signinSubmitButton = findViewById<Button>(R.id.signin_submit_button)
        val usernameEditText = findViewById<EditText>(R.id.sign_username)
        val passwordEditText = findViewById<TextInputEditText>(R.id.signin_password)
        val loginBackButton = findViewById<Button>(R.id.signin_back_button)
        val backToFilmButton = findViewById<Button>(R.id.back_to_homepage_button)

        cameraButton = findViewById(R.id.camera_button)
        capturedImageView = findViewById(R.id.captured_image)

        // Gestisci il click per passare alla seconda schermata
        loginButton.setOnClickListener {
            viewFlipper.displayedChild = 1
            loginBackButton.visibility = Button.VISIBLE
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
        loginBackButton.setOnClickListener {
            viewFlipper.displayedChild = 0
            loginBackButton.visibility = Button.GONE
        }

        // Gestisci il click per tornare alla homepage
        backToFilmButton.setOnClickListener {
            backToMainActivity()  // Naviga alla homepage
        }


        // Ottieni l'ID del film passato tramite Intent
        selectedMovieId = intent.getIntExtra("MOVIE_ID", -1)
        Log.d("LoginPageActivity", "Movie ID: $selectedMovieId")

        if (selectedMovieId == -1) {
            Toast.makeText(this, "Nessun film selezionato", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("LoginPageActivity", "Movie ID: $selectedMovieId")
        }

        cameraButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                openCamera()
            }
        }
    }

    // Funzione per effettuare il login
    private fun performLogin(username: String, password: String) {
        userViewModel.login(username, password) { success, errorMessage ->
            runOnUiThread {
                if (success) {
                    Toast.makeText(this, "Accesso eseguito", Toast.LENGTH_SHORT).show()
                    goToOverview()  // Naviga alla schermata di riepilogo
                } else {
                    Toast.makeText(this, "Accesso non riuscito: $errorMessage", Toast.LENGTH_SHORT).show()
                    findViewById<TextInputEditText>(R.id.signin_password).text?.clear()  // Pulisce la password
                }
            }
        }
    }

    // Funzione per navigare alla homepage
    private fun backToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Termina questa activity
    }

    // Funzione per navigare alla schermata di riepilogo (OverviewActivity)
    private fun goToOverview() {
        val movieId = selectedMovieId
        if (movieId != null && movieId != -1) {

            val intent = Intent(this, OverviewPageActivity::class.java).apply {
                putExtra("MOVIE_ID", movieId)  // Passa l'ID del film alla schermata successiva
            }
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Nessun ID film selezionato", Toast.LENGTH_SHORT).show()
            backToMainActivity()  // Torna alla homepage se nessun film è selezionato
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    @Deprecated("Deprecated in Java (33).")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data?.extras
            userPhoto = extras?.get("data") as Bitmap
            capturedImageView.setImageBitmap(userPhoto)

            // converti bitmap in bytearray per passarlo all'intent
            val stream = ByteArrayOutputStream()
            userPhoto?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            // invia i dati a OverviewPageActivity
            val intent = Intent(this, OverviewPageActivity::class.java).apply {
                putExtra("captured_image", byteArray)
                putExtra("MOVIE_ID", selectedMovieId)  //anche l'id del film
            }

            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Permesso fotocamera negato", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
