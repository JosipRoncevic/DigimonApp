package org.unizd.rma.roncevic

import android.content.Intent
import android.os.Bundle
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

        GlobalScope.launch {
            try {
                val response = RetrofitInstance.digimonApi.getAllDigimons()
                runOnUiThread {
                    response.body()?.let { digimonList ->
                        adapter = DigimonAdapter(digimonList) { digimon ->
                            // On item click, navigate to the detail activity
                            val intent = Intent(this@AllDigimonActivity, DigimonDetailActivity::class.java)
                            intent.putExtra("DIGIMON_NAME", digimon.name)
                            intent.putExtra("DIGIMON_IMAGE", digimon.img)
                            intent.putExtra("DIGIMON_LEVEL", digimon.level)
                            startActivity(intent)
                        }
                        recyclerView.adapter = adapter
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
