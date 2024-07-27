package progetto.cinema.cinestock.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import progetto.cinema.cinestock.R
import progetto.cinema.cinestock.ui.adapter.user.UserAdapter
import progetto.cinema.cinestock.ui.viewmodel.ContactsViewModel
import progetto.cinema.cinestock.models.user.User

class UserListActivity : AppCompatActivity() {

    private val userViewModel: ContactsViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        userAdapter = UserAdapter { user ->
            onUserSelected(user)
        }
        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        userViewModel.allUsers.observe(this, Observer { users ->
            users?.let {
                userAdapter.setUsers(it)
            }
        })

        // Check for contacts permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_CONTACTS), 1)
        } else {
            // Fetch contacts if permission is granted
            fetchContacts()
        }
    }

    private fun onUserSelected(user: User) {
        // Handle the user selection logic
        val resultIntent = Intent().apply {
            putExtra("selected_user", user)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun fetchContacts() {
        // Simulate fetching contacts and inserting into the database
        val users = listOf(
            User(firstName = "John", lastName = "Doe", phoneNumber = "123456789"),
            User(firstName = "Jane", lastName = "Doe", phoneNumber = "987654321")
        )
        users.forEach { user ->
            userViewModel.insert(user)
        }
    }
}
