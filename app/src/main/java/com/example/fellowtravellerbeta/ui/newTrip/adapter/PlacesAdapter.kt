package com.example.fellowtravellerbeta.ui.newTrip.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.data.network.google.response.PredictionResponse
import kotlinx.android.synthetic.main.places_item.view.*

class PlacesAdapter(private val placesList: List<PredictionResponse>) :

    RecyclerView.Adapter<PlacesAdapter.ExampleViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.places_item,
            parent, false
        )
        return ExampleViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = placesList[position]
        currentItem.description = currentItem.description.split(", Ελλάδα")[0]
        holder.title.text = currentItem.description
        holder.title.setOnClickListener {

        }

    }

    override fun getItemCount() = placesList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.place_item_textView

    }
}