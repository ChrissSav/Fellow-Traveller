package gr.fellow.fellow_traveller.ui.home.settings


import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentUserCarsBinding
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.car.CarAdapter
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.home.HomeViewModel


@AndroidEntryPoint
class UserCarsFragment : BaseFragment<FragmentUserCarsBinding>() {


    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentUserCarsBinding =
        FragmentUserCarsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.cars.observe(viewLifecycleOwner, Observer {
            (binding.myCarsRecycler.adapter as CarAdapter).submitList(it)
            binding.refresh.isRefreshing = false

            //if there are no cars to display, show specific message, else show cars
            if (it.isNullOrEmpty())
                binding.carSection.visibility = View.VISIBLE
            else {
                binding.myCarsRecycler.visibility = View.VISIBLE
                binding.carSection.visibility = View.GONE
            }
        })

        viewModel.loadCars.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.myCarsRecycler.visibility = View.GONE
                binding.carSection.visibility = View.GONE
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

            myCarsRecycler.adapter = CarAdapter {
                findNavController()?.navigate(R.id.action_userCarsFragment_to_carDetailsFragment, bundleOf("car" to it))
            }

            backButtons.setOnClickListener {
                onBackPressed()
            }

            refresh.setOnRefreshListener {
                viewModel.loadCars(true)
            }

            newCarButton.setOnClickListener {
                startActivityForResult(AddCarActivity::class, 1, null)
            }

            myCarsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy < 0) {
                        // Scrolling up
                        if (!newCarButton.isExtended)
                            newCarButton.extend()
                        //Log.i("makis", "Scrolling up")

                    } else if (dy > 10) {
                        if (newCarButton.isExtended)
                            newCarButton.shrink()
                        // Scrolling down
                        // Log.i("makis", " Scrolling down")

                    }
                }
            })

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