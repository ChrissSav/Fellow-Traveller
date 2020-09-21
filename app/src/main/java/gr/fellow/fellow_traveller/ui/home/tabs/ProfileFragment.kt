package gr.fellow.fellow_traveller.ui.home.tabs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentInfoBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.loadImageFromUrl


class ProfileFragment : Fragment() {


    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel.user.observe(viewLifecycleOwner, Observer { user ->

            with(binding) {
                userName.text = "${user.firstName} ${user.lastName}"
                userImage.loadImageFromUrl(user.picture)
                reviews.text = user.reviews.toString()
                rate.text = user.rate.toString()
                aboutMeInfo.text= user.aboutMe
            }


        })

        binding.settingsButton.setOnClickListener {
            val nav = Navigation.findNavController(view)
            nav.navigate(R.id.to_setting)

        }

        binding.messengerLink.setOnClickListener {
            val uriUrl: Uri = Uri.parse(getString(R.string.messenger_link, "regino29"))
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}