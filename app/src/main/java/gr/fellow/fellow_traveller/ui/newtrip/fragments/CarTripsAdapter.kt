package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.room.entites.CarEntity

class CarTripsAdapter(
    private val cars: MutableList<CarEntity>,
    private val listener: (CarEntity) -> Unit
) : RecyclerView.Adapter<CarTripsAdapter.ViewHolder>() {


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

        holder.brand.text = currentItem.brand
        holder.model.text = currentItem.model
        holder.plate.text = currentItem.plate
        holder.color.text = currentItem.color
        holder.delete.visibility = View.INVISIBLE

        holder.constraintLayout.setOnClickListener {
            listener(currentItem)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val brand: TextView = view.findViewById(R.id.textView_brand)
        val model: TextView = view.findViewById(R.id.textView_model)
        val plate: TextView = view.findViewById(R.id.textView_plate)
        val color: TextView = view.findViewById(R.id.textView_color)
        val constraintLayout: ConstraintLayout = view.findViewById(R.id.my_car_item_constraintLayout)
        val delete: ImageView = view.findViewById(R.id.imageView_delete)
    }

}