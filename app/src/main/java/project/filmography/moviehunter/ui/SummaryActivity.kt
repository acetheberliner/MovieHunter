package project.filmography.moviehunter.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Aggiungi questa importazione per usare lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.launch // Import per lanciare coroutine
import project.filmography.moviehunter.R
import project.filmography.moviehunter.data.entity.User
import project.filmography.moviehunter.ui.viewmodel.MovieViewModel
import project.filmography.moviehunter.network.giphy.GiphyApi
import project.filmography.moviehunter.ui.adapter.giphy.GifAdapter

class SummaryActivity : AppCompatActivity() {

    // Dichiarazione delle variabili per i componenti dell'interfaccia
    private lateinit var posterImage: ImageView
    private lateinit var priceTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var proceedButton: Button
    private lateinit var shareButton: Button
    private lateinit var gifRecyclerView: RecyclerView
    private lateinit var giphyAdapter: GifAdapter

    // ID del film passato dall'attività precedente
    private var movieId: Int? = null

    // Chiave API per accedere ai dettagli del film e a Giphy
    private val apiKey = "54403dbde09d7b532faa644c618e84cf"
    private val giphyApiKey = "49ChsckPtw8bgXsJfyLHGC9TLm1clE5L" // Sostituisci con la tua API key Giphy

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
        gifRecyclerView = findViewById(R.id.gif_recycler_view)

        gifRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        giphyAdapter = GifAdapter(emptyList())
        gifRecyclerView.adapter = giphyAdapter

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

            // Carica una GIF correlata al film da Giphy
            fetchGiphyGif(movieDetails.original_title)
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

    // Funzione per recuperare la GIF da Giphy per il film
    private fun fetchGiphyGif(movieTitle: String) {
        lifecycleScope.launch {
            try {
                val response = GiphyApi.getRetrofitService().getGifsForMovie(movieTitle, giphyApiKey)
                Log.d("SummaryActivity", "Giphy response: $response")

                val gifUrls = response.data.take(3).map { it.images.original.url }
                giphyAdapter.updateGifs(gifUrls)
            } catch (e: Exception) {
                Log.e("SummaryActivity", "Errore nel recuperare la GIF", e)
                Toast.makeText(this@SummaryActivity, "Errore nel recuperare la GIF", Toast.LENGTH_SHORT).show()
            }
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
