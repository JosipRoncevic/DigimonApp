package org.unizd.rma.roncevic

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DigimonDetailActivity : AppCompatActivity() {

    private lateinit var favoriteStar: ImageView
    private lateinit var digimonName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digimon_detail)

        val digimonImage = intent.getStringExtra("DIGIMON_IMAGE")
        val digimonLevel = intent.getStringExtra("DIGIMON_LEVEL")
        digimonName = intent.getStringExtra("DIGIMON_NAME") ?: ""

        val nameTextView: TextView = findViewById(R.id.digimonName)
        val levelTextView: TextView = findViewById(R.id.digimonLevel)
        val imageView: ImageView = findViewById(R.id.digimonImage)
        favoriteStar = findViewById(R.id.favoriteStar)

        nameTextView.text = digimonName
        levelTextView.text = digimonLevel
        Glide.with(this).load(digimonImage).into(imageView)

        updateFavoriteStar()

        favoriteStar.setOnClickListener {
            toggleFavorite()
        }
    }

    private fun updateFavoriteStar() {
        val isFavorite = isDigimonFavorite(digimonName)
        favoriteStar.setImageResource(
            if (isFavorite) R.drawable.baseline_circle_24 else R.drawable.outline_circle_24
        )
    }

    private fun toggleFavorite() {
        val sharedPref = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val isFavorite = isDigimonFavorite(digimonName)
        with(sharedPref.edit()) {
            if (isFavorite) {
                remove(digimonName)
            } else {
                putBoolean(digimonName, true)
            }
            apply()
        }
        updateFavoriteStar()
    }

    private fun isDigimonFavorite(digimonName: String): Boolean {
        val sharedPref = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        return sharedPref.getBoolean(digimonName, false)
    }
}
