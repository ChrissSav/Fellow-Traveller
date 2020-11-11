package gr.fellow.fellow_traveller.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.toPx
import kotlinx.android.synthetic.main.passenger_base_info_item.view.*


class PassengerAdapter(
    private val passengerList: MutableList<Passenger>,
    private val listener: (UserBase) -> Unit
) :
    RecyclerView.Adapter<PassengerAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.passenger_base_info_item,
            parent, false
        )
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
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
            image.loadImageFromUrl(passenger.user.picture)
            name.text = passenger.user.fullName
        }

    }

    override fun getItemCount() = passengerList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.passenger_username
        val image: ImageView = itemView.picture

    }
}

