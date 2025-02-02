package project.filmography.moviehunter.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import project.filmography.moviehunter.R
import project.filmography.moviehunter.data.entity.User
import project.filmography.moviehunter.ui.viewmodel.MovieViewModel

class SummaryActivity : AppCompatActivity() {

    // Dichiarazione delle variabili per i componenti dell'interfaccia
    private lateinit var posterImage: ImageView
    private lateinit var priceTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var proceedButton: Button
    private lateinit var shareButton: Button

    // ID del film passato dall'attività precedente
    private var movieId: Int? = null

    // Chiave API per accedere ai dettagli del film
    private val apiKey = "54403dbde09d7b532faa644c618e84cf"

    // ViewModel per la gestione dei dettagli del film
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.Factory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // Inizializzazione dei componenti UI
        posterImage = findViewById(R.id.poster_image)
        priceTextView = findViewById(R.id.price_text_view)
        titleTextView = findViewById(R.id.title_text_view)
        descriptionTextView = findViewById(R.id.description_text_view)
        proceedButton = findViewById(R.id.proceed_button)
        shareButton = findViewById(R.id.share_button)

        // Recupero dell'ID del film passato tramite Intent
        movieId = intent.getIntExtra("MOVIE_ID", -1)
        if (movieId == -1) {
            Toast.makeText(this, "Nessun film selezionato", Toast.LENGTH_SHORT).show()
            finish() // Chiude l'attività se l'ID del film non è valido
        } else {
            // Carica i dettagli del film se l'ID è valido
            movieViewModel.fetchSpecificMovieDetails(apiKey, movieId!!)
        }

        // Osservatore per i dettagli del film
        movieViewModel.movieDetails.observe(this) { movieDetails ->
            // Impostazione dei dati nel layout
            titleTextView.text = movieDetails.original_title
            descriptionTextView.text = movieDetails.overview
            priceTextView.text = "€4.99" // Prezzo fisso per il film
            val imageUrl = "https://image.tmdb.org/t/p/w500${movieDetails.poster_path}"
            Glide.with(this@SummaryActivity)
                .load(imageUrl)
                .transform(RoundedCorners(16)) // Applica angoli arrotondati all'immagine
                .into(posterImage)
        }

        // Azione per il pulsante "Procedi"
        proceedButton.setOnClickListener {
            val intent = Intent(this, ShippingActivity::class.java)
            startActivity(intent)
        }

        // Azione per il pulsante "Condividi"
        shareButton.setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_USER)
        }
    }

    // Gestisce il risultato dell'attività UserListActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_USER && resultCode == RESULT_OK) {
            val selectedUser = data?.getSerializableExtra("selected_user") as? User
            selectedUser?.let { onUserSelected(it) }
        }
    }

    // Funzione chiamata quando un utente viene selezionato
    private fun onUserSelected(user: User) {
        // Messaggio di condivisione
        val message = "Film inviato a ${user.name}"

        // Crea un intent per condividere il film con l'utente selezionato
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey ${user.name}, ho appena visto questo film di nome ${titleTextView.text}, ti consiglio di prenderlo in considerazione!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Condividi Film"))
    }

    companion object {
        // Codice per identificare il risultato dell'attività
        private const val REQUEST_CODE_USER = 1
    }
}
