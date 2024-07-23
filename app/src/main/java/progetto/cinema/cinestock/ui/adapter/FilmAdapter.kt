package progetto.cinema.cinestock.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import progetto.cinema.cinestock.R


class FilmAdapter(
    private val dataSource: MutableList<FilmModel>,
    private val listener: FilmAdapterListener?
) :
    RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    interface FilmAdapterListener {
        fun onFilmSelected(filmModel: FilmModel)
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val txtFilmName: TextView
        val txtFilmDescription: TextView

        init {
            // Define click listener for the ViewHolder's View
            txtFilmName = view.findViewById(R.id.movie_title)
            txtFilmDescription = view.findViewById(R.id.movie_description)
            imageView = view.findViewById(R.id.movie_image)
        }
    }

    fun updateFilmList(filmModelList: List<FilmModel>) {
        dataSource.clear()
        dataSource.addAll(filmModelList)
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_movie, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val filmModel = dataSource[position]
        viewHolder.txtFilmName.text = filmModel.name
        //viewHolder.txtFilmDescription.text = filmModel.description

        Glide.with(viewHolder.itemView).load(filmModel.pictureUrl)
            .into(viewHolder.imageView)

        viewHolder.itemView.setOnClickListener {
            Log.d("FILMADAPTER", "onFilmSelected filmModel: $filmModel")
            listener?.onFilmSelected(filmModel)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSource.size

}