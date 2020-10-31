package gr.fellow.fellow_traveller.ui.search.fragments

import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchResultsBinding
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel

@AndroidEntryPoint
class SearchResultsFragment : BaseFragment<FragmentSearchResultsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
   // private var tripsList = mutableListOf<Trip>()


    override fun getViewBinding(): FragmentSearchResultsBinding =
        FragmentSearchResultsBinding.inflate(layoutInflater)

    override fun setUpObservers() {}

    override fun setUpViews() {


    }

}