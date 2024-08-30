package progetto.cinema.cinestock.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import progetto.cinema.cinestock.R
import progetto.cinema.cinestock.data.database.UserDatabase
import progetto.cinema.cinestock.data.UserRepository
import progetto.cinema.cinestock.ui.adapter.user.UserAdapter
import progetto.cinema.cinestock.data.entity.User

class UserListActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        // Initialise UserRepository
        val userDao = UserDatabase.getDatabase(application, CoroutineScope(Dispatchers.IO)).userDao()
        userRepository = UserRepository(userDao)

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
            // Permission already granted, load contacts from device
            loadContactsFromDevice()
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
                // Permission granted, load contacts from device
                loadContactsFromDevice()
            } else {
                // Permission denied, return to SummaryActivity
                Toast.makeText(this, "Permission denied to read contacts", Toast.LENGTH_SHORT).show()
                returnToSummaryActivity()
            }
        }
    }

    private fun loadContactsFromDevice() {
        val contactsList = mutableListOf<User>()

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            if (nameIndex >= 0 && phoneNumberIndex >= 0) {
                while (it.moveToNext()) {
                    val name = it.getString(nameIndex) ?: "N/A"
                    val phoneNumber = it.getString(phoneNumberIndex) ?: "N/A"

                    // Creates a User object with the retrieved data
                    val user = User(name = name, phoneNumber = phoneNumber)
                    contactsList.add(user)
                }
            } else {
                // Handle the case when columns are not found
                Toast.makeText(this, "Error retrieving contact information", Toast.LENGTH_SHORT).show()

            }
        }

        // Perform the insertion of contacts into the database asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.insertAll(contactsList)
        }

        // Set the contacts in the RecyclerView Adapter
        userAdapter.setUsers(contactsList)
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
