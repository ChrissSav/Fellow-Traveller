package gr.fellow.fellow_traveller.ui.search.fragments

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchTripDetailsBinding
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.startActivityWithBundle
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapter
import gr.fellow.fellow_traveller.ui.user.UserInfoDetailsActivity

@AndroidEntryPoint
class SearchTripDetailsFragment : BaseFragment<FragmentSearchTripDetailsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var tripId = "0"
    private var userId: String? = null
    private var index: Int? = null


    override fun handleIntent() {
        tripId = requireArguments().getString("tripId").toString()
    }

    override fun getViewBinding(): FragmentSearchTripDetailsBinding =
        FragmentSearchTripDetailsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        index = viewModel.resultTrips.value?.indexOfFirst { it.id == tripId }
        if (index != null) {
            viewModel.resultTrips.value?.get(index!!)?.let {
                with(binding) {
                    userId = it.creatorUser.id
                    from.text = it.destFrom.title
                    to.text = it.destTo.title
                    time.text = it.time
                    day.text = it.date
                    userImage.loadImageFromUrl(it.creatorUser.picture)
                    username.text = it.creatorUser.fullName
                    rate.text = it.creatorUser.rate.toString()
                    price.text = resources.getString(R.string.price, it.price.toString())
                    seats.text = it.seatsStatus
                    bags.text = it.bags
                    pet.text = if (it.hasPet) resources.getString(R.string.yes) else resources.getString(R.string.no)
                    car.text = it.carBase.baseInfo
                    message.text = it.msg ?: resources.getString(R.string.no_driver_message)




                    description.text = "${price.text} ανά άτομο"

                    if (!it.passengers.isNullOrEmpty()) {
                        binding.passengerRecyclerView.adapter = PassengerAdapter(it.passengers, this@SearchTripDetailsFragment::onPassengerListener)
                    }

                }
            }
        } else {
            onBackPressed()
        }
    }

    override fun setUpViews() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.userImage.setOnClickListener {
            userId?.let {
                activity?.startActivityWithBundle(UserInfoDetailsActivity::class, bundleOf("userId" to it))
            }
        }

        binding.constraintLayoutMessenger.setOnClickListener {
            findNavController()?.navigate(R.id.action_searchTripDetailsFragment_to_bookTripFragment, bundleOf("index" to index))
        }

        binding.bookTrip.setOnClickListener {
            findNavController()?.navigate(R.id.action_searchTripDetailsFragment_to_bookTripFragment, bundleOf("index" to index))
        }
    }


    private fun onPassengerListener(user: UserBase) {
        activity?.startActivityWithBundle(UserInfoDetailsActivity::class, bundleOf("userId" to user.id))
    }


}
