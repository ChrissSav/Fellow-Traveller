package gr.fellow.fellow_traveller.ui.search.fragments

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchTripDetailsBinding
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapter

@AndroidEntryPoint
class SearchTripDetailsFragment : BaseFragment<FragmentSearchTripDetailsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var tripId = "0"


    override fun handleIntent() {
        tripId = requireArguments().getString("tripId").toString()
    }

    override fun getViewBinding(): FragmentSearchTripDetailsBinding =
        FragmentSearchTripDetailsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        val index = viewModel.resultTrips.value?.indexOfFirst { it.id == tripId }
        if (index != null) {
            viewModel.resultTrips.value?.get(index)?.let {
                with(binding) {
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


                    bookTrip.setOnClickListener {
                        /*findNavController()?.navigate(
                            R.id.action_searchTripDetailsFragment_to_bookTripFragment,
                            bundleOf("indexTrip" to index)
                        )*/


                    }

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
    }


    private fun onPassengerListener(user: UserBase) {
        Toast.makeText(this.context, user.id.trim(), Toast.LENGTH_SHORT).show()
    }


}
