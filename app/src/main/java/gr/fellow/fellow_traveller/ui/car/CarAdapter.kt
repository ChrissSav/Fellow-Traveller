package gr.fellow.fellow_traveller.ui.car


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.CarItemBinding
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.ui.extensions.CarDiffCallback

class CarAdapter(
    private val onCarItemClickListener: (Car) -> Unit
) : ListAdapter<Car, CarAdapter.ViewHolder>(CarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            val currentItem = currentList[position]
            binding.brand.text = currentItem.brand
            binding.model.text = currentItem.model
            binding.plate.text = currentItem.plate
            binding.color.text = currentItem.color
            binding.root.setOnClickListener {
                onCarItemClickListener(currentItem)
            }
        }
    }

    class ViewHolder(val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root)
}

