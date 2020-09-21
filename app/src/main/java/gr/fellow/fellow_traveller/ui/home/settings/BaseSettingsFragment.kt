package gr.fellow.fellow_traveller.ui.home.settings

import android.content.Intent
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
import gr.fellow.fellow_traveller.databinding.FragmentBaseSettingsBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.main.MainActivity


class BaseSettingsFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentBaseSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBaseSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.personalUserInfoButton.setOnClickListener {
            navController.navigate(R.id.action_baseSettingsFragment_to_accountSettingsFragment)

        }

        binding.manageUserCarsButton.setOnClickListener {
            navController.navigate(R.id.action_baseSettingsFragment_to_userCarsFragment)
        }

        binding.logoutButton.setOnClickListener {
            homeViewModel.logOut()
        }

        homeViewModel.user.observe(viewLifecycleOwner, Observer {
            with(binding){
                userEmailAddressTextView.text = it.email
                userFullNameTextView.text = "${it.firstName} ${it.lastName}"
                profilePictureSettings.loadImageFromUrl(it.picture)
            }
        })

        homeViewModel.logout.observe(viewLifecycleOwner, Observer {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}