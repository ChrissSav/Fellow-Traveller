package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.ChatInfoPassengerItemBinding
import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl


class ChatPassengersAdapter(
    private val passengersList: MutableList<Passenger>,
) : RecyclerView.Adapter<ChatPassengersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatInfoPassengerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = passengersList[position]

        with(holder) {
            binding.passengerName.text = currentItem.user.fullName
            binding.passengerImage.loadImageFromUrl(currentItem.user.picture)


        }
    }

    class ViewHolder(val binding: ChatInfoPassengerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = passengersList.size
}
