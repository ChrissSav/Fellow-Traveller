package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.PassengerFullInfoItemBinding
import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl


class PassengerFullInfoAdapter(
    private val passengerList: MutableList<Passenger>,
    private val listener: (UserBase) -> Unit
) : RecyclerView.Adapter<PassengerFullInfoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PassengerFullInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentPassenger = passengerList[position]

        with(holder) {
            binding.picture.loadImageFromUrl(currentPassenger.user.picture)
            binding.username.text = currentPassenger.user.fullName
            binding.seats.text =
                if (currentPassenger.seats == 1)
                    "${currentPassenger.seats} " + binding.pet.resources.getString(R.string.available_seats_singular)
                else
                    "${currentPassenger.seats} " + binding.pet.resources.getString(R.string.available_seats_plural)
            binding.pet.text = if (currentPassenger.pet) binding.pet.resources.getString(R.string.with_pet) else binding.pet.resources.getString(R.string.without_pet)
            binding.root.setOnClickListener {
                listener.invoke(currentPassenger.user)
            }
        }
    }

    class ViewHolder(val binding: PassengerFullInfoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = passengerList.size
}
