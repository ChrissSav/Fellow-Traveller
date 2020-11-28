package gr.fellow.fellow_traveller.ui.home.reviews

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentProfileReviewsBinding
import gr.fellow.fellow_traveller.domain.review.Review
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.ReviewsAdapter

@AndroidEntryPoint
class ProfileReviewsFragment : BaseFragment<FragmentProfileReviewsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private val reviewsList = mutableListOf<Review>()

    override fun getViewBinding(): FragmentProfileReviewsBinding =
        FragmentProfileReviewsBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.reviews.observe(this, Observer {
            reviewsList.clear()
            reviewsList.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false

            //if there are no reviews to display, show specific message, else show reviews
            if (it.isNullOrEmpty())
                binding.reviewsSection.visibility = VISIBLE
            else
                binding.reviewsSection.visibility = GONE

            binding.numRate.text = "${it.size} ${getString(R.string.total_reviews)}"
        })
    }

    override fun setUpViews() {
        binding.recyclerView.adapter = ReviewsAdapter(reviewsList)




        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadReviews()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

    }



}