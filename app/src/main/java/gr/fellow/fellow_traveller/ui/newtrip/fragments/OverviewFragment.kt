package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentOverviewBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.newtrip.NewTripActivity
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp
import gr.fellow.fellow_traveller.utils.getTimeFromTimestamp


@AndroidEntryPoint
class OverviewFragment : BaseFragment<FragmentOverviewBinding>(), View.OnClickListener {
    private val viewModel: NewTripViewModel by activityViewModels()


    override fun getViewBinding(): FragmentOverviewBinding =
        FragmentOverviewBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.success.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.next_fragment)
        })

    }

    override fun setUpViews() {
        with(binding) {

            rate.text = (activity as NewTripActivity).getUserRate().toString()

            from.text = viewModel.destinationFrom.value?.title
            to.text = viewModel.destinationTo.value?.title
            day.text = getDateFromTimestamp(viewModel.getTimestamp())
            time.text = getTimeFromTimestamp(viewModel.getTimestamp())
            price.text = getString(R.string.price, viewModel.price.value.toString())
            seats.text = viewModel.seats.value.toString()
            bags.text = viewModel.bags.value?.value.toString()
            pet.text = if (viewModel.pet.value!!) resources.getString(R.string.yes) else resources.getString(R.string.no)
            car.text = viewModel.car.value?.fullInfo
            userImage.loadImageFromUrl(viewModel.userInfo.value?.picture)
            username.text = viewModel.userInfo.value?.fullName
            message.text = if (viewModel.message.value.toString().isNotEmpty())
                viewModel.message.value.toString()
            else resources.getString(R.string.no_driver_message)




            ImageButtonNext.setOnClickListener {
                viewModel.registerTrip()
            }


            from.setOnClickListener(this@OverviewFragment)
            to.setOnClickListener(this@OverviewFragment)
            day.setOnClickListener(this@OverviewFragment)
            time.setOnClickListener(this@OverviewFragment)
            price.setOnClickListener(this@OverviewFragment)
            bags.setOnClickListener(this@OverviewFragment)
            seats.setOnClickListener(this@OverviewFragment)
            pet.setOnClickListener(this@OverviewFragment)
            car.setOnClickListener(this@OverviewFragment)
            message.setOnClickListener(this@OverviewFragment)

        }

    }

    override fun onClick(view: View) {
        when (view.tag) {
            "1" -> {
                findNavController()?.navigate(R.id.action_fragment_overview_to_destinationsFragment)
            }
            "2" -> {
                findNavController()?.navigate(R.id.action_fragment_overview_to_dateTimeFragment)
            }
            "3" -> {
                findNavController()?.navigate(R.id.action_fragment_overview_to_baseInfoFragment)
            }
            "4" -> {
                findNavController()?.navigate(R.id.action_fragment_overview_to_priceFragment)
            }
            "5" -> {
                onBackPressed()
            }
        }

    }


}