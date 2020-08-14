package gr.fellow.fellow_traveller.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import gr.fellow.fellow_traveller.databinding.FragmentOffersActiveTripsBinding
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.ui.home.adapter.ActiveTripsAdapter


class OffersActiveTripsFragment : Fragment() {


    private val homeViewModel: HomeViewModel by activityViewModels()
    private val tripsList = mutableListOf<TripResponse>()
    private var _binding: FragmentOffersActiveTripsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOffersActiveTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.recyclerView.adapter = ActiveTripsAdapter(tripsList, this::onTripClick)


        Handler().postDelayed({
            homeViewModel.tripsAsCreator.observe(viewLifecycleOwner, Observer {
                with(binding) {
                    tripsList.clear()
                    tripsList.addAll(it)
                    recyclerView.adapter?.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            })
        }, 250)
    }

    private fun onTripClick(trip: TripResponse) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}