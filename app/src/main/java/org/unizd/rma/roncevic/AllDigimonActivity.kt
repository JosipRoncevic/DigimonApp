package org.unizd.rma.roncevic

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AllDigimonActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DigimonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_digimon_activity)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val searchBar = findViewById<EditText>(R.id.searchBar)
        val searchButton = findViewById<Button>(R.id.searchButton)

        fetchAllDigimons()

        searchButton.setOnClickListener {
            val query = searchBar.text.toString()
            if (query.isNotBlank()) {
                if (query.equals("rookie", ignoreCase = true) || query.equals("champion", ignoreCase = true)) {
                    searchDigimonsByLevel(query)
                } else {
                    searchDigimonsByName(query)
                }
            } else if (query.isEmpty()){
                fetchAllDigimons()
            }else {
                Toast.makeText(this, "Please enter a name or level to search", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchAllDigimons() {
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.digimonApi.getAllDigimons()
                runOnUiThread {
                    response.body()?.let { digimons ->
                        adapter = DigimonAdapter(digimons) { digimon ->
                            navigateToDetailActivity(digimon)
                        }
                        recyclerView.adapter = adapter
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun searchDigimonsByName(name: String) {
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.digimonApi.getDigimonByName(name)
                runOnUiThread {
                    response.body()?.let { digimons ->
                        if (digimons.isNotEmpty()) {
                            adapter = DigimonAdapter(digimons) { digimon ->
                                navigateToDetailActivity(digimon)
                            }
                            recyclerView.adapter = adapter
                        } else {
                            Toast.makeText(this@AllDigimonActivity, "No Digimon found with name: $name", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@AllDigimonActivity, "Error fetching data for name: $name", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun searchDigimonsByLevel(level: String) {
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.digimonApi.getDigimonByLevel(level)
                runOnUiThread {
                    response.body()?.let { digimons ->
                        if (digimons.isNotEmpty()) {
                            adapter = DigimonAdapter(digimons) { digimon ->
                                navigateToDetailActivity(digimon)
                            }
                            recyclerView.adapter = adapter
                        } else {
                            Toast.makeText(this@AllDigimonActivity, "No Digimon found with level: $level", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@AllDigimonActivity, "Error fetching data for level: $level", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToDetailActivity(digimon: Digimon) {
        val intent = Intent(this, DigimonDetailActivity::class.java)
        intent.putExtra("DIGIMON_NAME", digimon.name)
        intent.putExtra("DIGIMON_IMAGE", digimon.img)
        intent.putExtra("DIGIMON_LEVEL", digimon.level)
        startActivity(intent)
    }
}

