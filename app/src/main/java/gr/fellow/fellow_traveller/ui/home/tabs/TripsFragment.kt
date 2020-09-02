package gr.fellow.fellow_traveller.ui.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gr.fellow.fellow_traveller.databinding.FragmentTripsBinding
import gr.fellow.fellow_traveller.ui.home.adapter.TripsViewPagerAdapter


class TripsFragment : Fragment() {

    private var _binding: FragmentTripsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            fragmentTripViewPager.adapter = TripsViewPagerAdapter(childFragmentManager)
            fragmentTripTabLayout.setupWithViewPager(fragmentTripViewPager)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}