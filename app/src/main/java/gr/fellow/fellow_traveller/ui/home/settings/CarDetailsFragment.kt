package gr.fellow.fellow_traveller.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import gr.fellow.fellow_traveller.databinding.FragmentCarDetailsBinding
import gr.fellow.fellow_traveller.domain.Car
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.onBackPressed

class CarDetailsFragment : Fragment() {

    private var _binding: FragmentCarDetailsBinding? = null
    private val binding get() = _binding!!
    private var car: Car? = null
    private val homeViewModel: HomeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        car = requireArguments().getParcelable("car")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCarDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        car?.let {
            binding.brand.text = it.brand
            binding.model.text = it.model
            binding.plate.text = it.plate
            binding.color.text = it.color
        }

        binding.backButtons.setOnClickListener {
            onBackPressed()
        }

        homeViewModel.carDeletedId.observe(viewLifecycleOwner, Observer {
            onBackPressed()
        })

        binding.delete.setOnClickListener {
            car?.let { car -> homeViewModel.deleteCar(car) }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}