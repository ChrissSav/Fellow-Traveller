package gr.fellow.fellow_traveller.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.ui.loadImageFromUrl
import kotlinx.android.synthetic.main.search_result_item.view.*

class SearchResultsAdapter(
    private val tripsList: MutableList<TripResponse>,
    private val listener: (TripResponse) -> Unit
) :
    RecyclerView.Adapter<SearchResultsAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.search_result_item,
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
            date.text = trip.getDate()
            time.text = trip.getTime()
            price.text = trip.price.toString()
            creatorImage.loadImageFromUrl(trip.creatorUser.picture)
            rate.text = trip.creatorUser.rate.toString()
            reviews.text = trip.creatorUser.reviews.toString()

            this.itemView.setOnClickListener {
                listener(trip)
            }
        }

    }

    override fun getItemCount() = tripsList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val destinationsFromTextView: TextView = itemView.from_search_item
        val destinationsToTextView: TextView = itemView.to_search_item
        val creatorName: TextView = itemView.name_search_item
        val date: TextView = itemView.date_search_item
        val time: TextView = itemView.time_search_item
        val price: TextView = itemView.price_search_item
        val creatorImage: ImageView = itemView.profile_search_item
        val rate: TextView = itemView.rate_search_item
        val reviews: TextView = itemView.review_search_item
    }
}

/*
class SearchResultsAdapter(
    private val tripsList: MutableList<TripResponse>,
    private val listener: (TripResponse) -> Unit
) :
    RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder( SearchResultItemBinding.inflate(inflater))

    }


    override fun getItemCount(): Int = tripsList.size

    override fun onBindViewHolder(holder: SearchResultsAdapter.ViewHolder, position: Int) = holder.bind(tripsList[position])


    inner class ViewHolder(val binding: SearchResultItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: TripResponse) {

            with(binding) {
                fromSearchItem.text = currentItem.destFrom.title
                toSearchItem.text = currentItem.destTo.title
                nameSearchItem.text = currentItem.creatorUser.fullName
                dateSearchItem.text = currentItem.getDate()
                timeSearchItem.text = currentItem.getTime()
                priceSearchItem.text = currentItem.price.toString()
                profileSearchItem.loadImageFromUrl(currentItem.creatorUser.picture)
                rateSearchItem.text = currentItem.creatorUser.rate.toString()
                reviewSearchItem.text = currentItem.creatorUser.reviews.toString()

                root.setOnClickListener {
                    listener(currentItem)
                }
            }
        }

    }*/
