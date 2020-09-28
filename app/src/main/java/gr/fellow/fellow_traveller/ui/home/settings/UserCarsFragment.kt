package gr.fellow.fellow_traveller.ui.home.settings


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentUserCarsBinding
import gr.fellow.fellow_traveller.domain.Car
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.car.CarAdapterSecond
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.onBackPressed


class UserCarsFragment : Fragment() {

    private var _binding: FragmentUserCarsBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var carsList = mutableListOf<Car>()
    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserCarsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.backButtons.setOnClickListener {
            onBackPressed()
        }



        binding.refresh.setOnRefreshListener {
            homeViewModel.loadCars()
        }

        /*homeViewModel.carDeletedId.observe(viewLifecycleOwner, Observer {
            val itemPosition = carsList.indexOf(it)
            carsList.remove(it)
            binding.myCarsRecycler.adapter?.notifyItemRemoved(itemPosition)
        })*/

        binding.newCarButton.setOnClickListener {
            val intent = Intent(activity, AddCarActivity::class.java)
            startActivityForResult(intent, 1)
        }

        Handler().postDelayed({
            homeViewModel.cars.observe(viewLifecycleOwner, Observer {
                carsList.clear()
                carsList.addAll(it)
                binding.myCarsRecycler.adapter?.notifyDataSetChanged()
                binding.refresh.isRefreshing = false
            })
        }, 250)



        with(binding) {
            myCarsRecycler.adapter = CarAdapterSecond(carsList) {
                navController.navigate(R.id.action_userCarsFragment_to_carDetailsFragment, bundleOf("car" to it))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}