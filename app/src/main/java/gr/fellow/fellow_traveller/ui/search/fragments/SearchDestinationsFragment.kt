package gr.fellow.fellow_traveller.ui.search.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchDestinationsBinding
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.findNavController
import gr.fellow.fellow_traveller.ui.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.locations.SelectDestinationActivity


@AndroidEntryPoint
class SearchDestinationsFragment : BaseFragment<FragmentSearchDestinationsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()


    override fun getViewBinding(): FragmentSearchDestinationsBinding =
        FragmentSearchDestinationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        with(viewModel) {
            destinationFrom.observe(viewLifecycleOwner, Observer {
                binding.destFromButton.setText(it.title)
            })

            destinationTo.observe(viewLifecycleOwner, Observer {
                binding.destToButton.setText(it.title)
            })
        }
    }

    override fun setUpViews() {
        with(binding) {
            destFromButton.setOnClickListener {
                val intent = Intent(activity, SelectDestinationActivity::class.java)
                startActivityForResult(intent, 1)
                activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

            }
            destToButton.setOnClickListener {
                val intent = Intent(activity, SelectDestinationActivity::class.java)
                intent.putExtra("info", "to")
                startActivityForResult(intent, 2)
                activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }

            backButton.setOnClickListener {
                onBackPressed()
            }

            searchButton.setOnClickListener {
                if (viewModel.destinationFrom.value != null && viewModel.destinationTo.value != null) {
                    viewModel.updateFilter()
                    findNavController()?.navigate(R.id.next_fragment)
                } else {
                    createAlerter(resources.getString(R.string.ERROR_FIELDS_REQUIRE))
                }
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val place = data?.getParcelableExtra<PlaceModel>("place")
                place?.let {
                    viewModel.setDestinationFrom(it)
                }
            }

        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val place = data?.getParcelableExtra<PlaceModel>("place")
                place?.let {
                    viewModel.setDestinationTo(it)
                }

            }

        }
    }


}