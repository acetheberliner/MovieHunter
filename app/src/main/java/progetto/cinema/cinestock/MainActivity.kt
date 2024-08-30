package progetto.cinema.cinestock

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import progetto.cinema.cinestock.ui.SignInActivity
import progetto.cinema.cinestock.ui.adapter.movie.MovieAdapter
import progetto.cinema.cinestock.ui.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.Factory(application)
    }
    private val apiKey = "e96d473555668ee67739012c7f140604"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val progressIndicator = findViewById<CircularProgressIndicator>(R.id.progressIndicator)
        val searchView = findViewById<SearchView>(R.id.search_view)
        val backButton = findViewById<ImageButton>(R.id.back_button)

        val adapter = MovieAdapter { movie ->
            val intent = Intent(this, SignInActivity::class.java).apply {
                putExtra("MOVIE_ID", movie.id)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (isNetworkAvailable()) {
                        progressIndicator.visibility = View.VISIBLE
                        movieViewModel.searchMovies(apiKey, it)
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

        movieViewModel.movies.observe(this, Observer { movies ->
            progressIndicator.visibility = View.GONE
            movies?.let { adapter.submitList(it) }
        })

        movieViewModel.searchResults.observe(this, Observer { searchResults ->
            progressIndicator.visibility = View.GONE
            searchResults?.let { adapter.submitList(it) }
        })

        handleNetworkOperations()

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun handleNetworkOperations() {
        if (isNetworkAvailable()) {
            movieViewModel.fetchTrendingMovies(apiKey)
        } else {
            showNoInternetConnectionMessage()
        }
    }

    private fun showNoInternetConnectionMessage() {
        Toast.makeText(this, "No internet connection available", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val searchView = findViewById<SearchView>(R.id.search_view)
        if (!searchView.isIconified) {
            searchView.onActionViewCollapsed()
            movieViewModel.fetchTrendingMovies(apiKey)
        } else {
            super.onBackPressed()
        }
    }
}
