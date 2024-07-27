package progetto.cinema.cinestock.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import progetto.cinema.cinestock.R

class ShippingActivity: AppCompatActivity() {

    private lateinit var addressEditText: EditText
    private lateinit var attentionTextView: TextView
    private lateinit var buyButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        addressEditText = findViewById(R.id.address_edit_text)
        attentionTextView = findViewById(R.id.attention_text_view)
        buyButton = findViewById(R.id.buy)

        buyButton.setOnClickListener {
            val address = addressEditText.text.toString()
            if (address.isNotEmpty() && address.contains("Street", ignoreCase = true)) { // checks if the input contains the word "street"
                val intent = Intent(this, EndActivity::class.java)
                startActivity(intent)
                finish()  // Finish this activity so the user cannot return to it
            } else {
                Toast.makeText(this, "Please enter an address", Toast.LENGTH_SHORT).show()
            }
        }
    }
}