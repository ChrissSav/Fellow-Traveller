package gr.fellow.fellow_traveller.ui.car


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.CarItemSecondBinding
import gr.fellow.fellow_traveller.domain.car.Car

class CarAdapterSecond(
    private val cars: MutableList<Car>,
    private val onCarItemClickListener: (Car) -> Unit
) : RecyclerView.Adapter<CarAdapterSecond.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarItemSecondBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cars.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val currentItem = cars[position]
            binding.car.text = currentItem.baseInfo
            binding.root.setOnClickListener {
                onCarItemClickListener(currentItem)
            }
        }

    }

    class ViewHolder(val binding: CarItemSecondBinding) : RecyclerView.ViewHolder(binding.root)
}