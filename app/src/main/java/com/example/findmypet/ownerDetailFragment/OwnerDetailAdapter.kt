package com.example.findmypet.ownerDetailFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findmypet.R
import com.example.findmypet.database.Pet
import com.example.findmypet.mainFragment.MainViewHolder

class OwnerDetailAdapter (
        private val pets: ArrayList<Pet>
        ): RecyclerView.Adapter<DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_listitem, parent, false)
        return DetailViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val pet = pets[position]
        holder.layout.findViewById<ImageView>(R.id.iv_avatar).setImageResource(R.drawable.ic_pet)
        holder.layout.findViewById<TextView>(R.id.tv_name).text = pet.name
        holder.layout.findViewById<TextView>(R.id.tv_latitude).text = pet.latitude.toString()
        holder.layout.findViewById<TextView>(R.id.tv_longitude).text = pet.longitude.toString()
    }
}

class DetailViewHolder(val layout: View): RecyclerView.ViewHolder(layout)
