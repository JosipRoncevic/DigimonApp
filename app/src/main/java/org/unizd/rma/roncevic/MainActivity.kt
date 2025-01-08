package org.unizd.rma.roncevic

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAllDigimons: Button = findViewById(R.id.btn_all_digimons)
        btnAllDigimons.setOnClickListener {
            val intent = Intent(this, AllDigimonActivity::class.java)
            startActivity(intent)
        }
    }
}