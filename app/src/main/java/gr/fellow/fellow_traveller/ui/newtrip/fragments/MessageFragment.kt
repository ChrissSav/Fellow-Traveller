package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentMessageBinding
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


class MessageFragment : Fragment() {
    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        newTripViewModel.message.observe(viewLifecycleOwner, Observer {
            binding.AddMessageFragmentEditText.setText(it)
        })

        binding.ImageButtonNext.setOnClickListener {
            newTripViewModel.setMessage(binding.AddMessageFragmentEditText.text.toString())

            if (newTripViewModel.message.value != null)
                navController.navigate(R.id.next_fragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}