package progetto.cinema.cinestock.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import progetto.cinema.cinestock.MovieDetailActivity
import progetto.cinema.cinestock.R
import progetto.cinema.cinestock.models.movie.TMDbMovie

class MovieAdapter(private val onClick: (TMDbMovie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies = emptyList<TMDbMovie>()

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.movie_title)
        val descriptionView: TextView = itemView.findViewById(R.id.movie_description)
        val imageView: ImageView = itemView.findViewById(R.id.movie_image)
        val buyButton: Button = itemView.findViewById(R.id.buy_button)

        fun bind(movie: TMDbMovie) {
            titleView.text = movie.title
            descriptionView.text = movie.overview
            Glide.with(imageView.context).load(movie.posterUrl).into(imageView)
            buyButton.setOnClickListener { onClick(movie) }

            // Aggiungi un listener per l'elemento principale per aprire i dettagli del film
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, MovieDetailActivity::class.java).apply {
                    putExtra("movie", movie)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = movies[position]
        holder.bind(current)
    }

    override fun getItemCount() = movies.size

    fun submitList(list: List<TMDbMovie>) {
        movies = list
        notifyDataSetChanged()
    }
}
