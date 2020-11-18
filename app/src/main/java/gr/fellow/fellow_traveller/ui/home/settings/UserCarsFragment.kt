package gr.fellow.fellow_traveller.ui.home.settings


import android.app.Activity
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentUserCarsBinding
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.car.CarAdapter
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.postDelay
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResult
import gr.fellow.fellow_traveller.ui.home.HomeViewModel


@AndroidEntryPoint
class UserCarsFragment : BaseFragment<FragmentUserCarsBinding>() {


    private val viewModel: HomeViewModel by activityViewModels()
    private var carsList = mutableListOf<Car>()


    override fun getViewBinding(): FragmentUserCarsBinding =
        FragmentUserCarsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        postDelay(250) {
            viewModel.cars.observe(viewLifecycleOwner, Observer {
                (binding.myCarsRecycler.adapter as CarAdapter).submitList(it)
                binding.refresh.isRefreshing = false
            })
        }
    }

    override fun setUpViews() {
        with(binding) {
            myCarsRecycler.adapter = CarAdapter {
                findNavController()?.navigate(R.id.action_userCarsFragment_to_carDetailsFragment, bundleOf("car" to it))
            }

            backButtons.setOnClickListener {
                onBackPressed()
            }

            refresh.setOnRefreshListener {
                viewModel.loadCars()
            }

            newCarButton.setOnClickListener {
                startActivityForResult(AddCarActivity::class, 1, null)
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