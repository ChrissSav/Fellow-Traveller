package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.loadImageFromUrl
import kotlinx.android.synthetic.main.active_trip_item_layout.view.*

/*class ActiveTripsAdapter(
    private val tripsList: MutableList<TripResponse>,
    private val listener: (TripResponse) -> Unit
) : RecyclerView.Adapter<ActiveTripsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ActiveTripItemLayoutBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tripsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tripsList[position])


    inner class ViewHolder(val binding: ActiveTripItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(trip: TripResponse) {

            with(binding) {
                destinationsFromTextView.text = trip.destFrom.title
                destinationsToTextView.text = trip.destTo.title
                creatorName.text = trip.creatorUser.fullName
                date.text = trip.getDate()
                time.text = trip.getTime()
                price.text = trip.price.toString()
                creatorImage.loadImageFromUrl(trip.creatorUser.picture)
                creatorRateAndReviews.text = "${trip.creatorUser.rate} (${trip.creatorUser.reviews})"

                root.setOnClickListener {
                    listener(trip)
                }
            }
        }

    }
}*/

class ActiveTripsAdapter(
    private val tripsList: MutableList<TripInvolved>,
    private val listener: (TripInvolved) -> Unit
) :
    RecyclerView.Adapter<ActiveTripsAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.active_trip_item_layout,
            parent, false
        )
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val trip = tripsList[position]

        with(holder) {
            destinationsFromTextView.text = trip.destFrom.title
            destinationsToTextView.text = trip.destTo.title
            creatorName.text = trip.creatorUser.fullName
            date.text = trip.date
            time.text = trip.time
            price.text = trip.price.toString()
            creatorImage.loadImageFromUrl(trip.creatorUser.picture)
            creatorRateAndReviews.text = "${trip.creatorUser.rate} (${trip.creatorUser.reviews})"

            this.itemView.setOnClickListener {
                listener(trip)
            }
        }

    }

    override fun getItemCount() = tripsList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val destinationsFromTextView: TextView = itemView.destinations_from_TextView
        val destinationsToTextView: TextView = itemView.destinations_to_TextView
        val creatorName: TextView = itemView.creator_name
        val date: TextView = itemView.date
        val time: TextView = itemView.time
        val price: TextView = itemView.price
        val creatorImage: ImageView = itemView.creator_image
        val creatorRateAndReviews: TextView = itemView.creator_rate_and_reviews

    }
}