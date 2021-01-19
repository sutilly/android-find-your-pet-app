package com.example.findmypet.mainFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.findmypet.R
import com.example.findmypet.database.Owner

class MainAdapter (
    private val dataSet: ArrayList<Owner>
        ) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_listitem, parent, false)
        return MainViewHolder(rootView)
    }

    override fun getItemCount(): Int  {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val owner = dataSet[position]
        holder.layout.findViewById<ImageView>(R.id.iv_avatar).setImageResource(R.drawable.ic_user)
        holder.layout.findViewById<TextView>(R.id.tv_name).text = owner.name
        holder.layout.findViewById<TextView>(R.id.tv_latitude).text = owner.latitude.toString()
        holder.layout.findViewById<TextView>(R.id.tv_longitude).text = owner.longitude.toString()

        holder.layout.setOnClickListener {
             Log.e("mainAdapter", "ownerId, $owner.id")
            holder.layout.findNavController().navigate(
                     MainFragmentDirections.actionMainFragmentToOwnerDetail(owner.id!!)
             )
         }

    }
}
class MainViewHolder(val layout: View): RecyclerView.ViewHolder(layout)
