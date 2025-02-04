package project.filmography.moviehunter.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import project.filmography.moviehunter.R

// Activity che gestisce la schermata di inserimento dell'indirizzo per la spedizione
class ShipmentPageActivity : AppCompatActivity() {

    private lateinit var addressEditText: EditText  // EditText per l'indirizzo
    private lateinit var attentionTextView: TextView  // TextView per avvisi
    private lateinit var buyButton: Button  // Bottone per acquistare
    private lateinit var backButton: Button  // Bottone per tornare alla schermata precedente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shipment_activity)  // Imposta il layout per questa activity

        // Trova gli elementi nel layout
        addressEditText = findViewById(R.id.address_edit_text)
        attentionTextView = findViewById(R.id.attention_text_view)
        buyButton = findViewById(R.id.buy)
        backButton = findViewById(R.id.back_to_homepage_button)

        // Gestione del click sul bottone "Compra"
        buyButton.setOnClickListener {
            val address = addressEditText.text.toString()  // Ottiene l'indirizzo inserito
            if (address.isNotEmpty() && address.lowercase().contains("via", ignoreCase = true)) {
                // Se l'indirizzo è valido, naviga alla schermata finale (EndActivity)
                val intent = Intent(this, FinalPageActivity::class.java)
                startActivity(intent)
                finish()  // Termina questa activity per evitare che l'utente ritorni su di essa
            } else {
                // Se l'indirizzo non è valido, mostra un messaggio di errore
                Toast.makeText(this, "Indirizzo non valido", Toast.LENGTH_SHORT).show()
            }
        }

        // Gestione del click sul bottone "Torna alla homepage"
        backButton.setOnClickListener {
            navigateToBackActivity()  // Naviga alla schermata di riepilogo
        }
    }

    // Funzione che naviga alla schermata di riepilogo (SummaryActivity)
    private fun navigateToBackActivity() {
        val intent = Intent(this, OverviewPageActivity::class.java)
        startActivity(intent)
        finish()  // Termina questa activity
    }
}
