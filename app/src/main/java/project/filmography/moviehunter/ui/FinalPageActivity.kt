package project.filmography.moviehunter.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import project.filmography.moviehunter.MainActivity
import project.filmography.moviehunter.R

// Activity finale che gestisce il comportamento dell'app dopo un'azione conclusiva
class FinalPageActivity : AppCompatActivity() {

    private lateinit var actionButton: Button  // Bottone che attiva la navigazione

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_activity)  // Imposta il layout per questa activity

        // Trova il bottone nel layout
        actionButton = findViewById(R.id.action_button)

        // Imposta il listener per il click del bottone
        actionButton.setOnClickListener {
            // Quando il bottone è cliccato, naviga alla MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //finish()  // Chiudi questa activity se non desiderato restare sulla schermata finale
        }
    }
}
