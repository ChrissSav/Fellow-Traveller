package gr.fellow.fellow_traveller.ui.car


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.CarItemOwnBinding
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.ui.extensions.CarDiffCallback

class CarOwnAdapter(
    private val onCarItemClickListener: (Car) -> Unit,
) : ListAdapter<Car, CarOwnAdapter.ViewHolder>(CarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarItemOwnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            val currentItem = currentList[position]
            binding.brandModel.text = "${currentItem.brand} ${currentItem.model}"
            binding.color.text = currentItem.color.title
            binding.edit.setOnClickListener {
                onCarItemClickListener(currentItem)
            }
        }
    }

    class ViewHolder(val binding: CarItemOwnBinding) : RecyclerView.ViewHolder(binding.root)
}

