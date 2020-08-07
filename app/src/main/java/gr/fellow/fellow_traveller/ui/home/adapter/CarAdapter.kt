package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.CarItemBinding
import gr.fellow.fellow_traveller.room.entites.CarEntity

class CarAdapter(
    private val cars: MutableList<CarEntity>,
    private val listener: (CarEntity) -> Unit
) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CarItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(cars[position])


    inner class ViewHolder(val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: CarEntity) {

            with(binding) {
                textViewBrand.text = currentItem.brand
                textViewModel.text = currentItem.model
                textViewPlate.text = currentItem.plate
                textViewColor.text = currentItem.color

                imageViewDelete.setOnClickListener {
                    listener(currentItem)
                }
            }
        }

    }
}