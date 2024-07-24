package progetto.cinema.cinestock

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import progetto.cinema.cinestock.models.movie.TMDbMovie
import progetto.cinema.cinestock.network.MovieApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailActivity : AppCompatActivity() {

    private val apiKey = "e96d473555668ee67739012c7f140604"
    private val baseUrl = "https://api.themoviedb.org/3/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra("MOVIE_ID", -1)
        if (movieId != -1) {
            fetchMovieDetails(movieId)
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(MovieApiService::class.java)

            try {
                val movie = service.getMovieDetails(movieId, apiKey)
                withContext(Dispatchers.Main) {
                    updateUI(movie)
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    private fun updateUI(movie: TMDbMovie) {
        val titleTextView = findViewById<TextView>(R.id.movie_title)
        val descriptionTextView = findViewById<TextView>(R.id.movie_description)
        val imageView = findViewById<ImageView>(R.id.movie_image)

        titleTextView.text = movie.original_title
        descriptionTextView.text = movie.overview

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w185${movie.poster_path}")
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(imageView)
    }
}
