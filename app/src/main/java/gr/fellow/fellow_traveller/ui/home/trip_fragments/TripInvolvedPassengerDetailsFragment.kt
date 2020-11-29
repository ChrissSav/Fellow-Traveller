package gr.fellow.fellow_traveller.ui.home.trip_fragments

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedPassengerDetailsBinding
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.startActivityWithBundle
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.PassengerFullInfoAdapter
import gr.fellow.fellow_traveller.ui.user.UserProfileDetailsActivity

@AndroidEntryPoint
class TripInvolvedPassengerDetailsFragment : BaseFragment<FragmentTripInvolvedPassengerDetailsBinding>() {

    private val args: TripInvolvedPassengerDetailsFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private var userId = ""


    override fun getViewBinding(): FragmentTripInvolvedPassengerDetailsBinding =
        FragmentTripInvolvedPassengerDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            userId = it.id
        })
    }

    override fun setUpViews() {

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        args.passengerList.let {
            binding.passengerRecyclerView.adapter = PassengerFullInfoAdapter(it.toMutableList(), this@TripInvolvedPassengerDetailsFragment::onPassengerListener)
        }

    }

    private fun onPassengerListener(user: UserBase) {
        if (user.id != userId)
            activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to user.id))
        else {
            findNavController()?.navigate(R.id.action_tripInvolvedPassengerDetailsFragment_to_destination_info)
        }
    }


}