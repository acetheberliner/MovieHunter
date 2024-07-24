package progetto.cinema.cinestock

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import progetto.cinema.cinestock.ui.LoginActivity
import progetto.cinema.cinestock.ui.MovieAdapter
import progetto.cinema.cinestock.viewmodel.MovieViewModel

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

        val adapter = MovieAdapter { movie ->
            // Passa l'ID del film selezionato a LoginActivity
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtra("MOVIE_ID", movie.id) // Passa l'ID del film
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchView = findViewById<SearchView>(R.id.search_view) // allows the user to enter and search for text
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { // called when the user submits the search query (by pressing the enter button or clicking the search button)
                query?.let { // checks if "query" is null. If it is not, calls the searchMovies method
                    progressIndicator.visibility = View.VISIBLE // shows progress indicator
                    movieViewModel.searchMovies(apiKey, it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean { // called whenever the text in the SearchView changes
                return false // not handling the text change event while the user types
            }
        })

        movieViewModel.movies.observe(this, Observer { movies ->
            progressIndicator.visibility = View.GONE // hides progress indicator
            movies?.let { adapter.submitList(it) }
        })

        // when searchMovies is executed and updates the results, the code inside Observer is executed
        movieViewModel.searchResults.observe(this, Observer { searchResults ->
            progressIndicator.visibility = View.GONE // hides progress indicator
            searchResults?.let { adapter.submitList(it) }
        })

        movieViewModel.fetchTrendingMovies(apiKey)
    }
}
