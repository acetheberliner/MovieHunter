package project.filmography.moviehunter.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import project.filmography.moviehunter.MainActivity
import project.filmography.moviehunter.R

class EndActivity : AppCompatActivity() {

    private lateinit var actionButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        actionButton = findViewById(R.id.action_button)

        // Set up the button click listener
        actionButton.setOnClickListener {
            // Navigate to the Movie List Activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //finish()
        }
    }
}
