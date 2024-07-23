package progetto.cinema.cinestock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView
import progetto.cinema.cinestock.models.movie.TMDbMovie

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Recupera il film dall'intento
        val movie = intent.getParcelableExtra<TMDbMovie>("movie")

        // Assegna i dati ai rispettivi elementi della UI
        if (movie != null) {
            val titleView: TextView = findViewById(R.id.movie_detail_title)
            val descriptionView: TextView = findViewById(R.id.movie_detail_description)
            val releaseDateView: TextView = findViewById(R.id.movie_detail_release_date)
            val voteAverageView: TextView = findViewById(R.id.movie_detail_vote_average)
            val imageView: ImageView = findViewById(R.id.movie_detail_image)

            titleView.text = movie.title
            descriptionView.text = movie.overview
            releaseDateView.text = "Release Date: ${movie.releaseDate}"
            voteAverageView.text = "Rating: ${movie.voteAverage}"
            Glide.with(this).load(movie.posterUrl).into(imageView)
        }
    }
}
