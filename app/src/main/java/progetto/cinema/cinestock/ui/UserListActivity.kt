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

        // Check for contacts permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_CONTACTS), PERMISSION_REQUEST_CODE)
        } else {
            // Load data from database
            loadData()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, load data from the database
                loadData()
            } else {
                // Permission denied, return to SummaryActivity
                returnToSummaryActivity()
            }
        }
    }

    private fun loadData() {
        // Load data from the database
        userViewModel.allUsers.observe(this, Observer { users ->
            users?.let {
                userAdapter.setUsers(it)
            }
        })
    }

    private fun returnToSummaryActivity() {
        // Create an intent to go back to SummaryActivity
        val intent = Intent(this, SummaryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Clear all other activities
        startActivity(intent)
        finish() // Close the current activity
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }
}
