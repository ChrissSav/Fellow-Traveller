package gr.fellow.fellow_traveller.ui.home.settings


import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentUserCarsBinding
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.car.CarOwnAdapter
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.home.HomeViewModel


@AndroidEntryPoint
class UserCarsFragment : BaseFragment<FragmentUserCarsBinding>() {


    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentUserCarsBinding =
        FragmentUserCarsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.cars.observe(viewLifecycleOwner, Observer {
            (binding.myCarsRecycler.adapter as CarOwnAdapter).submitList(it)
            binding.refresh.isRefreshing = false

            //if there are no cars to display, show specific message, else show cars
            if (it.isNullOrEmpty()) {
                binding.carSectionDontFound.visibility = View.VISIBLE
                binding.scrollViewHaveCars.visibility = View.GONE
            } else {
                binding.scrollViewHaveCars.visibility = View.VISIBLE
                binding.carSectionDontFound.visibility = View.GONE
            }
        })

        viewModel.loadCars.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.scrollViewHaveCars.visibility = View.GONE
                binding.carSectionDontFound.visibility = View.GONE
                binding.shimmerViewContainer.startShimmerWithVisibility()
            } else {
                binding.shimmerViewContainer.stopShimmerWithVisibility()
                binding.refresh.isRefreshing = false
            }
        })
    }

    override fun setUpViews() {

        viewModel.loadCars()

        with(binding) {

            myCarsRecycler.adapter = CarOwnAdapter {
                findNavController()?.navigate(R.id.action_userCarsFragment_to_carDetailsFragment, bundleOf("car" to it))
            }

            back.button.setOnClickListener {
                onBackPressed()
            }

            refresh.setOnRefreshListener {
                viewModel.loadCars(true)
            }

            newCarButton.setOnClickListener {
                startActivityForResult(AddCarActivity::class, 1, null)
            }

            buttonAddCarSecond.setOnClickListener {
                startActivityForResult(AddCarActivity::class, 1, null)
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.loadCars(true)
            }
        }
    }


}