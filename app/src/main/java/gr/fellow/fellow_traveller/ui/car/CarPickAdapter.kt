package gr.fellow.fellow_traveller.ui.car


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.CarItemPickBinding
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.ui.extensions.CarDiffCallback

class CarPickAdapter(
    private val onCarItemClickListener: (Car) -> Unit
) : ListAdapter<Car, CarPickAdapter.ViewHolder>(CarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarItemPickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            val currentItem = currentList[position]
            binding.car.text = currentItem.fullInfo
            binding.root.setOnClickListener {
                onCarItemClickListener(currentItem)
            }

            binding.car.setOnClickListener {
                onCarItemClickListener(currentItem)
            }
        }
    }

    class ViewHolder(val binding: CarItemPickBinding) : RecyclerView.ViewHolder(binding.root)
}

