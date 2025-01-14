package org.unizd.rma.roncevic

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RandomDigimonActivity : AppCompatActivity() {

    private lateinit var digimonList: List<Digimon>
    private lateinit var tvRandomName: TextView
    private lateinit var ivRandomDigimon: ImageView
    private lateinit var btnNextRandom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_digimon)

        tvRandomName = findViewById(R.id.random_digimon_name)
        ivRandomDigimon = findViewById(R.id.random_digimon_picture)
        btnNextRandom = findViewById(R.id.btn_next_random)

        fetchAllDigimons()

        btnNextRandom.setOnClickListener {
            showRandomDigimon()
        }
    }

    private fun fetchAllDigimons() {
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.digimonApi.getAllDigimons()
                if (response.isSuccessful) {
                    response.body()?.let {
                        digimonList = it
                        runOnUiThread {
                            showRandomDigimon()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showRandomDigimon() {
        if (::digimonList.isInitialized && digimonList.isNotEmpty()) {
            val randomDigimon = digimonList.random()
            tvRandomName.text = randomDigimon.name
            Glide.with(this)
                .load(randomDigimon.img)
                .into(ivRandomDigimon)
        } else {
            tvRandomName.text = "Loading Digimons..."
        }
    }
}
