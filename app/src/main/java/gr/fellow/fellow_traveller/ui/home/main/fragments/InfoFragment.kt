package gr.fellow.fellow_traveller.ui.home.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentInfoBinding
import gr.fellow.fellow_traveller.domain.sigleton.UserInfoSingle
import gr.fellow.fellow_traveller.ui.loadImage
import javax.inject.Inject

@AndroidEntryPoint
class InfoFragment : Fragment() {


    @Inject
    lateinit var userInfoSingle: UserInfoSingle

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
        binding.userName.text = "${userInfoSingle.firstName} ${userInfoSingle.lastName}"


        binding.userImage.loadImage(userInfoSingle.picture)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}