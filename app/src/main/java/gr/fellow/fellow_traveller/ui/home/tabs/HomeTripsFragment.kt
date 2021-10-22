package gr.fellow.fellow_traveller.ui.home.tabs

import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentHomeTripsBinding
import gr.fellow.fellow_traveller.ui.home.adapter.TripsViewPagerAdapter
import gr.fellow.fellow_traveller.ui.home.trip_fragments.TripsActiveTabFragment
import gr.fellow.fellow_traveller.ui.home.trip_fragments.TripsOffersTabFragment


@AndroidEntryPoint
class HomeTripsFragment : BaseFragment<FragmentHomeTripsBinding>() {

    private var tripsActiveTabFragment = TripsActiveTabFragment()
    private var tripsOffersFragment = TripsOffersTabFragment()

    override fun getViewBinding(): FragmentHomeTripsBinding =
        FragmentHomeTripsBinding.inflate(layoutInflater)

    override fun setUpObservers() {}

    override fun setUpViews() {
        val tripsViewPagerAdapter = TripsViewPagerAdapter(childFragmentManager)
        tripsViewPagerAdapter.addFragment(tripsActiveTabFragment, getString(R.string.active))
        tripsViewPagerAdapter.addFragment(tripsOffersFragment, getString(R.string.history))
        binding.fragmentTripViewPager.adapter = tripsViewPagerAdapter
        binding.fragmentTripTabLayout.setupWithViewPager(binding.fragmentTripViewPager)

    }
}