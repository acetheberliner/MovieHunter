package progetto.cinema.cinestock.ui.adapter.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import progetto.cinema.cinestock.R
import progetto.cinema.cinestock.models.user.User

class UserAdapter(private val onUserClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users = emptyList<User>()

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstNameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val lastNameTextView: TextView = itemView.findViewById(R.id.textViewLastName)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.textViewPhoneNumber)

        fun bind(user: User) {
            firstNameTextView.text = user.firstName
            lastNameTextView.text = user.lastName
            phoneNumberTextView.text = user.phoneNumber

            itemView.setOnClickListener {
                onUserClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = users[position]
        holder.bind(currentUser)
    }

    override fun getItemCount() = users.size

    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }
}
