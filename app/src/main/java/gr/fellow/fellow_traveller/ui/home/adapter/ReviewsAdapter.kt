package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.ReviewItemLayoutBinding
import gr.fellow.fellow_traveller.domain.review.Review
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp


class ReviewsAdapter(
    private val reviewList: MutableList<Review>
) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReviewItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = reviewList[position]

        with(holder) {
            binding.picture.loadImageFromUrl(currentItem.user.picture)
            binding.username.text = currentItem.user.fullName
            binding.rate.text = currentItem.rate.toString()
            binding.date.text = getDateFromTimestamp(currentItem.timestamp, "d MMM yyyy")
        }
    }

    class ViewHolder(val binding: ReviewItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = reviewList.size
}
