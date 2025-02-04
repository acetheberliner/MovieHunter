package project.filmography.moviehunter.ui.adapter.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.filmography.moviehunter.R
import project.filmography.moviehunter.data.entity.User

// Adapter per la lista degli utenti
class UserAdapter(private val onUserClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // Lista degli utenti (inizializzata come vuota)
    private var users = emptyList<User>()

    // ViewHolder che contiene i riferimenti alle views per ogni item
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Riferimenti ai TextView per nome e numero di telefono
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.textViewPhoneNumber)

        // Funzione per "bindare" i dati dell'utente all'UI
        fun bind(user: User) {
            nameTextView.text = user.name
            phoneNumberTextView.text = user.phoneNumber

            // Imposta il listener per il click sull'item
            itemView.setOnClickListener {
                onUserClick(user)
            }
        }
    }

    // Crea una nuova istanza di ViewHolder per ogni elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact_activity, parent, false)
        return UserViewHolder(itemView)
    }

    // Associa i dati dell'utente alla ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = users[position]
        holder.bind(currentUser)
    }

    // Restituisce il numero totale di elementi nella lista
    override fun getItemCount(): Int = users.size

    // Funzione per aggiornare la lista degli utenti
    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()  // Notifica il RecyclerView di un cambiamento
    }
}
