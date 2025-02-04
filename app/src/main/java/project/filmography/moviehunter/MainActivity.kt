package project.filmography.moviehunter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import project.filmography.moviehunter.ui.adapter.movie.MovieAdapter
import project.filmography.moviehunter.ui.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.Factory(application)
    }
    private val tmdbApiKey = "54403dbde09d7b532faa644c618e84cf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Inizializza le view
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val progressIndicator = findViewById<CircularProgressIndicator>(R.id.progressIndicator)
        val searchView = findViewById<SearchView>(R.id.search_view)
        val backButton = findViewById<ImageButton>(R.id.back_button)

        // Imposta l'adapter per il RecyclerView
        val adapter = MovieAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Listener per la ricerca
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (isNetworkAvailable()) {
                        progressIndicator.visibility = View.VISIBLE
                        movieViewModel.searchMovies(tmdbApiKey, it)
                    } else {
                        showNoInternetConnectionMessage()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Mostra la tastiera quando il SearchView viene cliccato
        val searchEditText: EditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchView.setOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
        }

        // Osserva i risultati della ricerca e li mostra nell'adapter
        movieViewModel.movies.observe(this) { movies ->
            progressIndicator.visibility = View.GONE
            movies?.let { adapter.submitList(it) }
        }

        movieViewModel.searchResults.observe(this) { searchResults ->
            progressIndicator.visibility = View.GONE
            searchResults?.let { adapter.submitList(it) }
        }

        // Gestisce le operazioni di rete
        handleNetworkOperations()

        // Listener per il bottone di ritorno
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // Controlla la connessione di rete
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Gestisce il caricamento dei film di tendenza in base alla connessione di rete
    private fun handleNetworkOperations() {
        if (isNetworkAvailable()) {
            movieViewModel.fetchPopularMovies(tmdbApiKey)
        } else {
            showNoInternetConnectionMessage()
        }
    }

    // Mostra un messaggio di errore se la connessione internet è assente
    private fun showNoInternetConnectionMessage() {
        Toast.makeText(this, "Connessione internet assente", Toast.LENGTH_SHORT).show()
    }
}
