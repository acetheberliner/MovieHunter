package progetto.cinema.cinestock

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra("MOVIE_ID", -1)

        // This is a simplified example, ideally you'd fetch movie details from a ViewModel
        val movie = MovieRepository.getMovieById(movieId)

        findViewById<TextView>(R.id.movie_detail_title).text = movie.title
        findViewById<TextView>(R.id.movie_detail_description).text = movie.description
        findViewById<TextView>(R.id.movie_detail_price).text = "$${movie.price}"
        findViewById<ImageView>(R.id.movie_detail_image).setImageResource(movie.imageResId)
    }
}


