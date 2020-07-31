package gr.fellow.fellow_traveller.ui.home.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fellowtravellerbeta.data.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentUserCarsBinding
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.CarAdapter
import gr.fellow.fellow_traveller.ui.location.adapter.PlacesAdapter


class UserCarsFragment : Fragment() {

    private var _binding: FragmentUserCarsBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var mAdapter: CarAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var carsList: MutableList<CarEntity> = mutableListOf()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserCarsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        mLayoutManager = LinearLayoutManager(view.context)

        binding.backButtons.setOnClickListener {
            activity?.onBackPressed()
        }


        initializeRecycle()

        homeViewModel.cars.observe(viewLifecycleOwner, Observer {
            carsList.clear()
            carsList.addAll(it)
            binding.myCarsRecycler.adapter?.notifyDataSetChanged()
            binding.refresh.isRefreshing = false
        })

        binding.refresh.setOnRefreshListener {
            homeViewModel.loadCars()
        }


        binding.newCarButton.setOnClickListener {
            navController.navigate(R.id.action_userCarsFragment_to_addCarFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRecycle() {
        mAdapter = CarAdapter(carsList) {

        }
        with(binding) {
            myCarsRecycler.layoutManager = mLayoutManager
            myCarsRecycler.adapter = mAdapter
        }


    }
}