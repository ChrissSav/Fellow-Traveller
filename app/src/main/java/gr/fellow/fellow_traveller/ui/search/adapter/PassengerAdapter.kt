package gr.fellow.fellow_traveller.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.PassengerBaseInfoItemBinding
import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.toPx


class PassengerAdapter(
    private val passengerList: MutableList<Passenger>,
    private val listener: (UserBase) -> Unit,
) : RecyclerView.Adapter<PassengerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PassengerBaseInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val passenger = passengerList[position]

        with(holder) {
            when (position) {
                0 -> {
                    itemView.setPadding(20.toPx, 10.toPx, 10.toPx, 10.toPx)
                }
                passengerList.size - 1 -> {
                    itemView.setPadding(10.toPx, 10.toPx, 20.toPx, 10.toPx)
                }
                else -> {
                    itemView.setPadding(10.toPx, 10.toPx, 10.toPx, 10.toPx)
                }
            }
            itemView.setOnClickListener {
                listener(passenger.user)
            }
            binding.picture.loadImageFromUrl(passenger.user.picture)
            binding.passengerUsername.text = passenger.user.fullName

            when (passenger.seats) {
                1 -> {  //Booked 1 seat
                    binding.seat1.visibility = View.VISIBLE
                    binding.seat2.visibility = View.GONE
                    binding.seat3.visibility = View.GONE
                    binding.seat4.visibility = View.GONE

                }
                2 -> {  //Booked 2 seats
                    binding.seat1.visibility = View.VISIBLE
                    binding.seat2.visibility = View.VISIBLE
                    binding.seat3.visibility = View.GONE
                    binding.seat4.visibility = View.GONE
                }
                3 -> {  //Booked 3 seats
                    binding.seat1.visibility = View.VISIBLE
                    binding.seat2.visibility = View.VISIBLE
                    binding.seat3.visibility = View.VISIBLE
                    binding.seat4.visibility = View.GONE
                }
                else -> { // More than 3 seats
                    binding.seat1.visibility = View.VISIBLE
                    binding.seat2.visibility = View.VISIBLE
                    binding.seat3.visibility = View.VISIBLE
                    binding.seat4.visibility = View.VISIBLE
                }
            }
            // Display if passenger has a pet
            if (passenger.pet)
                binding.pet.visibility = View.VISIBLE
            else
                binding.pet.visibility = View.GONE


        }

    }

    override fun getItemCount() = passengerList.size

    class ViewHolder(val binding: PassengerBaseInfoItemBinding) : RecyclerView.ViewHolder(binding.root)

}

