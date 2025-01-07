package org.unizd.rma.roncevic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DigimonAdapter(private val digimonList: List<Digimon>) : RecyclerView.Adapter<DigimonAdapter.DigimonViewHolder>() {

    class DigimonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val digimonName: TextView = itemView.findViewById(R.id.digimonName)
        val digimonLevel: TextView = itemView.findViewById(R.id.digimonLevel)
        val digimonImage: ImageView = itemView.findViewById(R.id.digimonImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigimonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.digimon_item, parent, false)
        return DigimonViewHolder(view)
    }

    override fun onBindViewHolder(holder: DigimonViewHolder, position: Int) {
        val digimon = digimonList[position]
        holder.digimonName.text = digimon.name
        holder.digimonLevel.text = digimon.level
        Glide.with(holder.itemView.context).load(digimon.img).into(holder.digimonImage)
    }

    override fun getItemCount(): Int {
        return digimonList.size
    }
}