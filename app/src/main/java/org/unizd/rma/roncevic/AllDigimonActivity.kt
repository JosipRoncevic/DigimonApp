package org.unizd.rma.roncevic

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.unizd.rma.roncevic.Digimon
import org.unizd.rma.roncevic.R
import org.unizd.rma.roncevic.RetrofitInstance

class AllDigimonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_digimon_activity)

        getDigimons()
    }

    private fun getDigimons() {
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.digimonApi.getAllDigimons()
                runOnUiThread {
                    response.body()?.let { digimonList ->
                        setUI(digimonList)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setUI(digimonList: List<Digimon>) {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DigimonAdapter(digimonList)
    }

}
