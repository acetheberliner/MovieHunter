package progetto.cinema.cinestock.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import progetto.cinema.cinestock.R

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Recupera il film dall'intento
        val movie = intent.getParcelableExtra<Movie>("movie")

        // Assegna i dati ai rispettivi elementi della UI
        if (movie != null) {
            val titleView: TextView = findViewById(R.id.movie_detail_title)
            val descriptionView: TextView = findViewById(R.id.movie_detail_description)
            val priceView: TextView = findViewById(R.id.movie_detail_price)
            val imageView: ImageView = findViewById(R.id.movie_detail_image)

            titleView.text = movie.title
            descriptionView.text = movie.description
            priceView.text = "$${movie.price}"
            imageView.setImageResource(movie.imageResId)
        }
    }
}


