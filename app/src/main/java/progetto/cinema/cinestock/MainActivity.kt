package progetto.cinema.cinestock

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val adapter = MovieAdapter { movie ->
            // Avvia la schermata di login quando si clicca su un film
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieViewModel.movies.observe(this, Observer { movies ->
            movies?.let { adapter.submitList(it) }
        })

        movieViewModel.fetchTrendingMovies(apiKey)
    }
}
