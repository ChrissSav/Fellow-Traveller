package gr.fellow.fellow_traveller.ui.home.settings


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import gr.fellow.fellow_traveller.databinding.FragmentUserCarsBinding
import gr.fellow.fellow_traveller.domain.Car
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.dialogs.DeleteConfirmationDialog
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.CarAdapter


class UserCarsFragment : Fragment() {

    private var _binding: FragmentUserCarsBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var mAdapter: CarAdapter
    private var carsList = mutableListOf<Car>()
    private lateinit var navController: NavController
    private lateinit var deleteDialog: DeleteConfirmationDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserCarsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.backButtons.setOnClickListener {
            activity?.onBackPressed()
        }


        initializeRecycle()



        binding.refresh.setOnRefreshListener {
            homeViewModel.loadCars()
        }

        homeViewModel.carDeletedId.observe(viewLifecycleOwner, Observer {
            val itemPosition = carsList.indexOf(it)
            carsList.remove(it)
            binding.myCarsRecycler.adapter?.notifyItemRemoved(itemPosition)
        })

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRecycle() {
        mAdapter = CarAdapter(carsList) { car ->
            activity?.let {
                deleteDialog = DeleteConfirmationDialog(requireActivity(), car.plate) {
                    deleteDialog.dismiss()
                    if (it)
                        homeViewModel.deleteCar(car)
                }
                fragmentManager?.let { it1 -> deleteDialog.show(it1, "dateDialog") }
            }

        }
        with(binding) {
            myCarsRecycler.layoutManager = GridLayoutManager(context, 2);
            myCarsRecycler.adapter = mAdapter
        }
    }


}