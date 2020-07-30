package gr.fellow.fellow_traveller.ui.home.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import gr.fellow.fellow_traveller.databinding.FragmentAccountSettingsBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.loadImageFromUrl


class AccountSettingsFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentAccountSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel.user.observe(viewLifecycleOwner, Observer {
            with(binding) {
                PersonalSettingsActivityEditTextFirstName.setText(it.firstName)
                PersonalSettingsActivityEditTextLastName.setText(it.lastName)
                PersonalSettingsActivityEditTextAboutMe.setText(it.aboutMe)
                PersonalSettingsActivityEditTextPhone.setText(it.phone)
                userImage.loadImageFromUrl(it.picture)

                PersonalSettingsActivityButtonClose.setOnClickListener {
                    activity?.onBackPressed()
                }
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}