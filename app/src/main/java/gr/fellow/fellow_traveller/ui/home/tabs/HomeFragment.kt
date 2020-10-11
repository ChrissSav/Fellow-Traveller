package gr.fellow.fellow_traveller.ui.home.tabs

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.databinding.FragmentHomeBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.newtrip.NewTripActivity
import gr.fellow.fellow_traveller.ui.search.SearchTripActivity
import gr.fellow.fellow_traveller.ui.startActivityForResult

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.userWelcomeTextView.text = it.firstName
        })
    }

    override fun setUpViews() {
        binding.offerSection.setOnClickListener {
            startActivityForResult(NewTripActivity::class, 1)
        }

        binding.searchButton.setOnClickListener {
            startActivityForResult(SearchTripActivity::class, 2)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val trip = data?.getParcelableExtra<Trip>("trip")
                trip?.let {
                    viewModel.addTripCreate(it)
                }
            }

        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val trip = data?.getParcelableExtra<Trip>("trip")
                trip?.let {
                    viewModel.addTripPassenger(it)
                }
            }

        }
    }


}