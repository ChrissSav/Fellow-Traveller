package gr.fellow.fellow_traveller.ui.home.tabs

import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentHomeTripsBinding
import gr.fellow.fellow_traveller.ui.home.adapter.TripsViewPagerAdapter
import gr.fellow.fellow_traveller.ui.home.trip_fragments.TripsOffersTabFragment
import gr.fellow.fellow_traveller.ui.home.trip_fragments.TripsTakePartTabFragment


@AndroidEntryPoint
class HomeTripsFragment : BaseFragment<FragmentHomeTripsBinding>() {

    private var tripsTakesPartFragment = TripsTakePartTabFragment()
    private var tripsOffersFragment = TripsOffersTabFragment()

    override fun getViewBinding(): FragmentHomeTripsBinding =
        FragmentHomeTripsBinding.inflate(layoutInflater)

    override fun setUpObservers() {}

    override fun setUpViews() {
        val tripsViewPagerAdapter = TripsViewPagerAdapter(childFragmentManager)
        tripsViewPagerAdapter.addFragment(tripsTakesPartFragment, "Αναζητήσεις")
        tripsViewPagerAdapter.addFragment(tripsOffersFragment, "Προσφορές")
        binding.fragmentTripViewPager.adapter = tripsViewPagerAdapter
        binding.fragmentTripTabLayout.setupWithViewPager(binding.fragmentTripViewPager)

        binding.clearButton.setOnClickListener {
            if (binding.fragmentTripTabLayout.selectedTabPosition == 0)
                tripsTakesPartFragment.resetTrips()
            else
                tripsOffersFragment.resetTrips()
        }
    }
}