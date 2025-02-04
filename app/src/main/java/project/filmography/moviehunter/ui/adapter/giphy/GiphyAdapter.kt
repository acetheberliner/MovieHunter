package project.filmography.moviehunter.ui.adapter.giphy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.filmography.moviehunter.R

// Adapter per le GIF
class GifAdapter(private var gifUrls: List<String>) : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    // Funzione per aggiornare la lista di GIF
    fun updateGifs(newGifs: List<String>) {
        gifUrls = newGifs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_giphy_activity, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gifUrl = gifUrls[position]
        Glide.with(holder.itemView.context).load(gifUrl).override(500, 500).into(holder.gifImageView)
    }

    override fun getItemCount() = gifUrls.size

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gifImageView: ImageView = itemView.findViewById(R.id.gif_image_view)
    }
}


