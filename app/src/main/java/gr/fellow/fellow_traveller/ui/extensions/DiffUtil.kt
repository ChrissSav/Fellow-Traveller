package gr.fellow.fellow_traveller.ui.extensions

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import gr.fellow.fellow_traveller.domain.car.Car

class CarDiffCallback : DiffUtil.ItemCallback<Car>() {
    override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem == newItem
    }
}