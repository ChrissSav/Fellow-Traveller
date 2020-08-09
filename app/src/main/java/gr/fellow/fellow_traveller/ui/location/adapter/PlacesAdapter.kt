package gr.fellow.fellow_traveller.ui.location.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse

class PlacesAdapter(
    private val places: MutableList<PredictionResponse>,
    private val listener: (PredictionResponse) -> Unit
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.place_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return places.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = places[position]
        currentItem.description =
            currentItem.description.split(", Ελλάδα".toRegex()).toTypedArray()[0]
        holder.title.text = currentItem.description



        holder.title.setOnClickListener {
            listener(currentItem)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.place_item_textView)
    }

}