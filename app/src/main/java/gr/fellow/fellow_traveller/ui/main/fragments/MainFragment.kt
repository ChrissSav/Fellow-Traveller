package gr.fellow.fellow_traveller.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentLoginBinding
import gr.fellow.fellow_traveller.databinding.FragmentMainBinding
import gr.fellow.fellow_traveller.ui.register.RegisterActivity


class MainFragment : Fragment() {

    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.buttonLogin.setOnClickListener {
            navController.navigate(R.id.next_fragment)
        }

        binding.buttonRegister.setOnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}