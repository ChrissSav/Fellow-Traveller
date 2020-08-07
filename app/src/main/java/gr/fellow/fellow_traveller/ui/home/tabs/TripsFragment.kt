package gr.fellow.fellow_traveller.ui.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gr.fellow.fellow_traveller.databinding.FragmentTripsBinding
import gr.fellow.fellow_traveller.ui.home.adapter.TripsViewPagerAdapter
import gr.fellow.fellow_traveller.ui.home.trips.TripsOffersFragment
import gr.fellow.fellow_traveller.ui.home.trips.TripsTakesPartFragment


class TripsFragment : Fragment() {

    private var _binding: FragmentTripsBinding? = null
    private val binding get() = _binding!!
    private lateinit var tripsOffersFragment: TripsOffersFragment
    private lateinit var tripsTakesPartFragment: TripsTakesPartFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tripsOffersFragment = TripsOffersFragment()
        tripsTakesPartFragment = TripsTakesPartFragment()

        val tripsViewPagerAdapter = TripsViewPagerAdapter(childFragmentManager)

        with(binding) {

            //Attaching fragments to tabLayout
            tripsViewPagerAdapter.addFragment(tripsOffersFragment, "Προσφορές")
            tripsViewPagerAdapter.addFragment(tripsTakesPartFragment, "Αναζητήσεις")

            fragmentTripViewPager.adapter = tripsViewPagerAdapter
            fragmentTripTabLayout.setupWithViewPager(fragmentTripViewPager)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}