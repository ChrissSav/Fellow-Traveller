package gr.fellow.fellow_traveller.ui.home.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentAccountBinding
import gr.fellow.fellow_traveller.databinding.FragmentHomeBinding
import gr.fellow.fellow_traveller.domain.sigleton.UserInfoSingle
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var userInfoSingle: UserInfoSingle


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
        binding.userWelcomeTextView.text = userInfoSingle.firstName
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}