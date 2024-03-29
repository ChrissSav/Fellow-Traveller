package gr.fellow.fellow_traveller.ui.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.PlacePopularItemBinding
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse

class PlacesPopularAdapter(
    private val places: MutableList<PredictionResponse>,
    private val listener: (PredictionResponse) -> Unit
) : RecyclerView.Adapter<PlacesPopularAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlacePopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = places[position]

        with(holder) {
            currentItem.description = currentItem.description.split(", Ελλάδα".toRegex()).toTypedArray()[0]

            binding.title.text = currentItem.description
            binding.title.setOnClickListener {
                listener(currentItem)
            }
        }
    }

    class ViewHolder(val binding: PlacePopularItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = places.size


}