package gr.fellow.fellow_traveller.ui.home.trip

import android.widget.Toast
import androidx.navigation.fragment.navArgs
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedPassengerDetailsBinding
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.adapter.PassengerFullInfoAdapter


class TripInvolvedPassengerDetailsFragment : BaseFragment<FragmentTripInvolvedPassengerDetailsBinding>() {

    private val args: TripInvolvedPassengerDetailsFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentTripInvolvedPassengerDetailsBinding =
        FragmentTripInvolvedPassengerDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {}

    override fun setUpViews() {

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        args.passengerList.let {
            binding.passengerRecyclerView.adapter = PassengerFullInfoAdapter(it.toMutableList(), this@TripInvolvedPassengerDetailsFragment::onPassengerListener)
        }

    }

    private fun onPassengerListener(user: UserBase) {
        Toast.makeText(this.context, user.id.trim(), Toast.LENGTH_SHORT).show()
    }


}