package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentOverviewBinding
import gr.fellow.fellow_traveller.ui.*
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


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

            textViewFrom.text = viewModel.destinationFrom.value?.title
            textViewTo.text = viewModel.destinationTo.value?.title
            textViewPickUpPoint.text = viewModel.destinationPickUp.value?.title
            day.text = getDayFromTimestamp(viewModel.getTimestamp())
            time.text = getTimeFromTimestamp(viewModel.getTimestamp())
            price.text = getString(R.string.price, viewModel.price.value.toString())
            seats.text = viewModel.seats.value.toString()
            bags.text = viewModel.bags.value.toString()
            pet.text = if (viewModel.pet.value!!) resources.getString(R.string.yes) else resources.getString(R.string.no)
            car.text = "${viewModel.car.value?.brand} ${viewModel.car.value?.model}"
            userImage.loadImageFromUrl(viewModel.userInfo.value?.picture.toString())
            username.text = viewModel.userInfo.value?.fullName
            message.text = if (viewModel.message.value.toString().isNotEmpty())
                viewModel.message.value.toString()
            else resources.getString(R.string.no_driver_message)




            ImageButtonNext.root.setOnClickListener {
                viewModel.registerTrip()

            }



            textViewFrom.setOnClickListener(this@OverviewFragment)
            textViewTo.setOnClickListener(this@OverviewFragment)
            textViewPickUpPoint.setOnClickListener(this@OverviewFragment)
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
            "6" -> {
                findNavController()?.navigate(R.id.action_fragment_overview_to_pickUpFragment)
            }

        }

    }


}