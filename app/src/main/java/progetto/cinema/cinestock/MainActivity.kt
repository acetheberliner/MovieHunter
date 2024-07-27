package progetto.cinema.cinestock

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
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
        val searchView = findViewById<SearchView>(R.id.search_view) // allows the user to enter and search for text
        val backButton = findViewById<ImageButton>(R.id.back_button)

        val adapter = MovieAdapter { movie ->
            // Passes the ID of the selected film to LoginActivity
            val intent = Intent(this, SignInActivity::class.java).apply {
                putExtra("MOVIE_ID", movie.id) // Passes the film ID
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

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

        // set listener for backButton
        backButton.setOnClickListener {
            onBackPressed() // call return method
        }

    }

    override fun onBackPressed() {
        val searchView = findViewById<SearchView>(R.id.search_view)
        // checks whether the SearchView is open
        if (!searchView.isIconified) {
            // if SearchView is open, it restores the movie list by closing the SearchView
            searchView.onActionViewCollapsed() // closes the SearchView
            movieViewModel.fetchTrendingMovies(apiKey) // reload list of trending movies
        } else {
            // if the SearchView is already closed, close the Activity
            super.onBackPressed()
        }
    }
}
