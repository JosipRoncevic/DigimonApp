package org.unizd.rma.roncevic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteDigimonActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DigimonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_digimons)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadFavoriteDigimons()
    }

    private fun loadFavoriteDigimons() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val allDigimons = fetchDigimonsFromApi()

                val favoriteNames = getFavoriteNames()

                val favoriteDigimons = allDigimons.filter { favoriteNames.contains(it.name) }

                withContext(Dispatchers.Main) {
                    if (favoriteDigimons.isNotEmpty()) {
                        adapter = DigimonAdapter(favoriteDigimons) { digimon ->
                            val intent = Intent(this@FavouriteDigimonActivity, DigimonDetailActivity::class.java)
                            intent.putExtra("DIGIMON_NAME", digimon.name)
                            intent.putExtra("DIGIMON_IMAGE", digimon.img)
                            intent.putExtra("DIGIMON_LEVEL", digimon.level)
                            startActivity(intent)
                        }
                        recyclerView.adapter = adapter
                    } else {
                        Toast.makeText(
                            this@FavouriteDigimonActivity,
                            "No favorite Digimons found.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@FavouriteDigimonActivity,
                        "Failed to load Digimons.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private suspend fun fetchDigimonsFromApi(): List<Digimon> {
        return try {
            val response = RetrofitInstance.digimonApi.getAllDigimons()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun getFavoriteNames(): Set<String> {
        val sharedPref = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val favoriteNames = sharedPref.all.keys
        return favoriteNames
    }

}
