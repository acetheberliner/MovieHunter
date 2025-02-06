package project.filmography.moviehunter.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import project.filmography.moviehunter.MainActivity
import project.filmography.moviehunter.R

// Activity finale che gestisce il comportamento dell'app dopo un'azione conclusiva
class FinalPageActivity : AppCompatActivity() {

    private lateinit var actionButton: Button  // Bottone che attiva la navigazione

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_activity)  // Imposta il layout per questa activity

        val capturedImageView = findViewById<ImageView>(R.id.final_captured_image)

        val byteArray = intent.getByteArrayExtra("captured_image")
        if (byteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            // Crea una nuova bitmap con bordi arrotondati
            val roundedBitmap = getRoundedCornerBitmap(bitmap, 16)  // raggio degli angoli arrotondati
            capturedImageView.setImageBitmap(roundedBitmap)
        }

        // Trova il bottone nel layout
        actionButton = findViewById(R.id.action_button)

        // Imposta il listener per il click del bottone
        actionButton.setOnClickListener {
            // Quando il bottone è cliccato, naviga alla MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //finish()  // Chiudi questa activity se non desiderato restare sulla schermata finale
        }
    }

    private fun getRoundedCornerBitmap(bitmap: Bitmap, radius: Int): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rectF = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
        paint.isAntiAlias = true
        canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return output
    }
}
