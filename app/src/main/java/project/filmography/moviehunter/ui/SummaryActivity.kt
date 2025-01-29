package project.filmography.moviehunter.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import project.filmography.moviehunter.R
import project.filmography.moviehunter.data.entity.User
import project.filmography.moviehunter.ui.viewmodel.MovieViewModel

class SummaryActivity : AppCompatActivity() {

    private lateinit var posterImage: ImageView
    private lateinit var priceTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var proceedButton: Button
    private lateinit var shareButton: Button
    private lateinit var sentToTextView: TextView

    private var movieId: Int? = null

    private val apiKey = "e96d473555668ee67739012c7f140604"

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.Factory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        posterImage = findViewById(R.id.poster_image)
        priceTextView = findViewById(R.id.price_text_view)
        titleTextView = findViewById(R.id.title_text_view)
        descriptionTextView = findViewById(R.id.description_text_view)
        proceedButton = findViewById(R.id.proceed_button)
        shareButton = findViewById(R.id.share_button)
        sentToTextView = findViewById(R.id.sent_to_text_view)

        movieId = intent.getIntExtra("MOVIE_ID", -1)
        Log.d("SummaryActivity", "Received movie ID: $movieId")

        if (movieId != null && movieId != -1) {
            movieId?.let {
                movieViewModel.fetchMovieDetails(apiKey, it)
            }
        } else {
            Toast.makeText(this, "Nessun film selezionato", Toast.LENGTH_SHORT).show()
            finish()
        }

        movieViewModel.movieDetails.observe(this) { movieDetails ->
            titleTextView.text = movieDetails.original_title
            descriptionTextView.text = movieDetails.overview
            priceTextView.text = "€4.99"
            val imageUrl = "https://image.tmdb.org/t/p/w500${movieDetails.poster_path}"
            Glide.with(this@SummaryActivity).load(imageUrl).transform(RoundedCorners(16)).into(posterImage)
        }

        proceedButton.setOnClickListener {
            val intent = Intent(this, ShippingActivity::class.java)
            startActivity(intent)
        }

        shareButton.setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_USER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_USER && resultCode == RESULT_OK) {
            val selectedUser = data?.getSerializableExtra("selected_user") as? User
            selectedUser?.let {
                onUserSelected(it)
            }
        }
    }

    private fun onUserSelected(user: User) {
        val message = "Film inviato a ${user.name}"
        sentToTextView.text = message
        sentToTextView.visibility = View.VISIBLE

        // send an intent to share the movie with the selected user
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey, ho appena visto questo film di nome ${titleTextView.text}, ti consiglio di prenderlo in considerazione!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Condividi Film"))
    }

    companion object {
        private const val REQUEST_CODE_USER = 1
    }
}
