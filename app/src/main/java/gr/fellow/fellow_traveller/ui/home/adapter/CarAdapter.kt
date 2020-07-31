package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.room.entites.CarEntity

class CarAdapter(
    private val cars: MutableList<CarEntity>,
    private val listener: (CarEntity) -> Unit
) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.car_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cars.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = cars[position]
        holder.title.text = "${currentItem.brand}, ${currentItem.model}  ${currentItem.plate}"

        holder.title.setOnClickListener {
            listener(currentItem)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.car_speces_settings_adapter)
    }

}