package gr.fellow.fellow_traveller.ui.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.PlaceItemBinding
import gr.fellow.fellow_traveller.domain.trip.Destination

class DestinationAdapter(
    private val places: MutableList<Destination>,
    private val listener: (Destination) -> Unit,
) : RecyclerView.Adapter<DestinationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = places[position]

        with(holder) {
            binding.title.text = currentItem.title
            binding.title.setOnClickListener {
                listener(currentItem)
            }
        }
    }

    class ViewHolder(val binding: PlaceItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = places.size


}