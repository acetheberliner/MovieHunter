package progetto.cinema.cinestock

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moviesContainer: ScrollView = findViewById(R.id.movies_container)

        for (i in 1..10) {
            val movieLayout = LinearLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, resources.getDimensionPixelSize(R.dimen.marginBottom))
                }
                orientation = LinearLayout.HORIZONTAL
                setBackgroundColor(Color.parseColor("#90EE90"))
                setPadding(
                    resources.getDimensionPixelSize(R.dimen.padding),
                    resources.getDimensionPixelSize(R.dimen.padding),
                    resources.getDimensionPixelSize(R.dimen.padding),
                    resources.getDimensionPixelSize(R.dimen.padding)
                )
            }

            val movieImage = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    0,
                    1f
                ).apply {
                    marginEnd = resources.getDimensionPixelSize(R.dimen.padding)
                }
                setImageResource(R.drawable.img1) // Assicurati di avere img1 nella cartella drawable
                contentDescription = getString(R.string.movie_image_desc)
                scaleType = ImageView.ScaleType.FIT_XY
            }

            val movieDetailsLayout = LinearLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
                orientation = LinearLayout.VERTICAL
                setPadding(8.dpToPx(), 0, 0, 0)
            }

            val movieDescription = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    0,
                    1f
                )
                text = "Descrizione del film $i"
                textSize = 16f
            }

            val movieButton = Button(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = android.view.Gravity.END
                }
                text = "Dettagli"
            }

            movieDetailsLayout.addView(movieDescription)
            movieDetailsLayout.addView(movieButton)
            movieLayout.addView(movieImage)
            movieLayout.addView(movieDetailsLayout)
            moviesContainer.addView(movieLayout)
        }
    }

    // Funzione di estensione per convertire dp in pixel
    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}
