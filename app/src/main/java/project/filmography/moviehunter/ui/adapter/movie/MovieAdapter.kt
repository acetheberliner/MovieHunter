package project.filmography.moviehunter.ui.adapter.movie

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import project.filmography.moviehunter.R
import project.filmography.moviehunter.models.movie.TMDbMovie
import project.filmography.moviehunter.ui.SignInActivity

// Visualizzazione dei film nel RecyclerView
class MovieAdapter :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // Lista dei film da visualizzare
    private var movies = emptyList<TMDbMovie>()

    // ViewHolder per ogni singolo elemento della lista
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.movie_title)
        private val descriptionView: TextView = itemView.findViewById(R.id.movie_description)
        private val imageView: ImageView = itemView.findViewById(R.id.movie_image)
        private val buyButton: Button = itemView.findViewById(R.id.buy_button)

        // Metodo per associare i dati del film agli elementi del layout
        fun bind(movie: TMDbMovie) {
            titleView.text = movie.original_title
            descriptionView.text = movie.overview

            // Carica l'immagine del film usando Glide con angoli arrotondati
            Glide.with(imageView.context).load("https://image.tmdb.org/t/p/w185${movie.poster_path}")
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .placeholder(R.drawable.placeholder)  // Immagine di placeholder nel caso di caricamento
                .error(R.drawable.error)  // Immagine di errore in caso di errore di caricamento
                .into(imageView)

            // Listener per il pulsante "Compra"
            buyButton.setOnClickListener {
                openLoginActivity(movie.id)  // Passa l'ID del film all'attività di login
            }
        }

        // Funzione per aprire la SignInActivity con l'ID del film
        private fun openLoginActivity(movieId: Int) {
            Log.d("MovieAdapter", "Passing movie ID: $movieId to LoginActivity")
            // Crea un'intent per avviare la SignInActivity, passando l'ID del film come dato extra
            val intent = Intent(itemView.context, SignInActivity::class.java).apply {
                putExtra("MOVIE_ID", movieId)  // Passa l'ID del film come parametro
            }
            itemView.context.startActivity(intent)  // Avvia l'intent
        }
    }

    // Metodo chiamato per creare una nuova view holder, che gestisce l'elemento della lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Inflazione del layout dell'elemento della lista
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(itemView)  // Restituisce il view holder con il layout appena creato
    }

    // Metodo chiamato per associare i dati alla view holder creata
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = movies[position]  // Ottieni il film dalla lista
        holder.bind(current)  // Associa i dati del film alla view holder
    }

    // Ritorna il numero totale di elementi nella lista
    override fun getItemCount() = movies.size

    // Metodo per aggiornare la lista dei film
    fun submitList(list: List<TMDbMovie>) {
        movies = list  // Imposta la nuova lista di film
        notifyDataSetChanged()  // Notifica che la lista è cambiata e il RecyclerView deve essere aggiornato
    }
}
