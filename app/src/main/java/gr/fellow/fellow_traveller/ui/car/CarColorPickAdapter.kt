package gr.fellow.fellow_traveller.ui.car


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.CarColorItemBinding
import gr.fellow.fellow_traveller.domain.car.CarColor
import gr.fellow.fellow_traveller.ui.extensions.CarColorDiffCallback

class CarColorPickAdapter(
    private val onCarItemClickListener: (CarColor) -> Unit,
) : ListAdapter<CarColor, CarColorPickAdapter.ViewHolder>(CarColorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarColorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {

            val currentItem = currentList[position]

            binding.title.text = currentItem.title
            binding.color.backgroundTintList = ColorStateList.valueOf(Color.parseColor(currentItem.hex))

            if (Color.parseColor(currentItem.hex) == Color.parseColor("#000000")) {
                binding.whiteStroke.visibility = View.VISIBLE
            } else {
                binding.whiteStroke.visibility = View.GONE
            }
            binding.root.setOnClickListener {
                onCarItemClickListener(currentItem)
            }
        }
    }

    class ViewHolder(val binding: CarColorItemBinding) : RecyclerView.ViewHolder(binding.root)
}

