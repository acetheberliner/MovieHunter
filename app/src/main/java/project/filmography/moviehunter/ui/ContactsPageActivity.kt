package project.filmography.moviehunter.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.filmography.moviehunter.R
import project.filmography.moviehunter.data.entity.User
import project.filmography.moviehunter.ui.adapter.user.UserAdapter
import project.filmography.moviehunter.ui.viewmodel.ContactsViewModel

class ContactsPageActivity : AppCompatActivity() {

    private val contactsViewModel: ContactsViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private var contactsList: List<User> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contacts_activity)

        // Inizializzazione UI
        val backButton = findViewById<ImageButton>(R.id.back_button)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Configurazione barra di ricerca
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredContacts = contactsList.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }
                userAdapter.setUsers(filteredContacts)
                return true
            }
        })

        // Configurazione RecyclerView
        userAdapter = UserAdapter { user -> onUserSelected(user) }
        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Verifica e richiesta permessi per i contatti
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_CONTACTS), PERMISSION_REQUEST_CODE
            )
        } else {
            loadContactsFromDevice()
        }

        // Azione tasto indietro
        backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    // Azione quando un utente viene selezionato
    private fun onUserSelected(user: User) {
        val resultIntent = Intent().apply {
            putExtra("selected_user", user)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    // Gestione della risposta alla richiesta di permessi
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContactsFromDevice()
            } else {
                Toast.makeText(this, "Permesso negato per leggere i contatti", Toast.LENGTH_SHORT).show()
                returnToSummaryActivity()
            }
        }
    }

    // Caricamento contatti dal dispositivo
    private fun loadContactsFromDevice() {
        CoroutineScope(Dispatchers.IO).launch {
            val retrievedContacts = mutableListOf<User>()
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
                        retrievedContacts.add(User(name = name, phoneNumber = phoneNumber))
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@ContactsPageActivity, "Errore nel recupero dei contatti", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Aggiornamento della lista contatti
            contactsList = retrievedContacts
            contactsViewModel.insertAll(contactsList)
            runOnUiThread { userAdapter.setUsers(contactsList) }
        }
    }

    // Ritorno all'attività precedente in caso di mancato permesso
    private fun returnToSummaryActivity() {
        val intent = Intent(this, OverviewPageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }
}
