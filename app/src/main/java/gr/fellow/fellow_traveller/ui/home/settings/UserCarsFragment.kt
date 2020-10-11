package gr.fellow.fellow_traveller.ui.home.settings


import android.app.Activity
import android.content.Intent
import android.os.Handler
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentUserCarsBinding
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.car.CarAdapterSecond
import gr.fellow.fellow_traveller.ui.findNavController
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.onBackPressed
import gr.fellow.fellow_traveller.ui.startActivityForResult


@AndroidEntryPoint
class UserCarsFragment : BaseFragment<FragmentUserCarsBinding>() {


    private val viewModel: HomeViewModel by activityViewModels()
    private var carsList = mutableListOf<Car>()


    override fun getViewBinding(): FragmentUserCarsBinding =
        FragmentUserCarsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        Handler().postDelayed({
            viewModel.cars.observe(viewLifecycleOwner, Observer {
                carsList.clear()
                carsList.addAll(it)
                binding.myCarsRecycler.adapter?.notifyDataSetChanged()
                binding.refresh.isRefreshing = false
            })
        }, 250)
    }

    override fun setUpViews() {
        with(binding) {
            myCarsRecycler.adapter = CarAdapterSecond(carsList) {
                findNavController()?.navigate(R.id.action_userCarsFragment_to_carDetailsFragment, bundleOf("car" to it))
            }

            backButtons.setOnClickListener {
                onBackPressed()
            }

            refresh.setOnRefreshListener {
                viewModel.loadCars()
            }

            newCarButton.setOnClickListener {
                startActivityForResult(AddCarActivity::class, 1)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.loadCarsLocal()
            }
        }
    }


}