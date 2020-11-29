package gr.fellow.fellow_traveller.ui.user.fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentUserReviewsBinding
import gr.fellow.fellow_traveller.domain.review.Review
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.adapter.ReviewsAdapter
import gr.fellow.fellow_traveller.ui.user.UserInfoDetailsViewModel


@AndroidEntryPoint
class UserReviewsFragment : BaseFragment<FragmentUserReviewsBinding>() {

    private val viewModel: UserInfoDetailsViewModel by activityViewModels()
    private val reviewsList = mutableListOf<Review>()

    override fun getViewBinding(): FragmentUserReviewsBinding =
        FragmentUserReviewsBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.reviews.observe(this, Observer {
            reviewsList.clear()
            reviewsList.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false

            binding.numRate.text = "${it.size} ${getString(R.string.total_ratings)}"
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