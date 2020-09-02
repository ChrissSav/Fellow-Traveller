package gr.fellow.fellow_traveller.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import gr.fellow.fellow_traveller.ui.home.trips.TripsOffersFragment
import gr.fellow.fellow_traveller.ui.home.trips.TripsTakesPartFragment

class TripsViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TripsOffersFragment()
            else -> TripsTakesPartFragment()
        }
    }

    override fun getCount(): Int = 2


    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Προσφερόμενα"
            else -> "Εμπλεκόμενα"
        }
    }

}