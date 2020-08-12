package gr.fellow.fellow_traveller.ui.home.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import gr.fellow.fellow_traveller.databinding.FragmentHomeBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.newtrip.NewTripActivity
import gr.fellow.fellow_traveller.ui.search.SearchTripActivity

//@AndroidEntryPoint

class HomeFragment : Fragment() {


    private val homeViewModel: HomeViewModel by activityViewModels()


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        homeViewModel.user.observe(viewLifecycleOwner, Observer {
            binding.userWelcomeTextView.text = it.firstName

        })

        binding.offerSection.setOnClickListener {
            val intent = Intent(activity, NewTripActivity::class.java)
            startActivity(intent)
        }

        binding.searchButton.setOnClickListener {
            val intent = Intent(activity, SearchTripActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}