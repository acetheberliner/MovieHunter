package progetto.cinema.cinestock.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import progetto.cinema.cinestock.R
import progetto.cinema.cinestock.ui.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.Factory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = MovieAdapter { movie ->
            // Qui va il codice per gestire il click sul film
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieViewModel.allMovies.observe(this, Observer { movies ->
            movies?.let { adapter.submitList(it) }
        })
    }
}
