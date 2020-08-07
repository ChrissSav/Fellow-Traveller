package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.ActiveTripItemLayoutBinding
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.ui.loadImageFromUrl

class ActiveTripsAdapter(
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
}