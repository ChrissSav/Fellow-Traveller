package gr.fellow.fellow_traveller.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentLoginBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.home.main.HomeActivity
import gr.fellow.fellow_traveller.ui.main.LoginViewModel

@WithFragmentBindings
class LoginFragment : Fragment() {


    private val loginViewModel: LoginViewModel by activityViewModels()

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        binding.buttonLogin.setOnClickListener {
            if (binding.emailEditText.text.isEmpty() && binding.passwordEditText.text.isEmpty()) {
                createSnackBar(view, resources.getString(R.string.ERROR_FIELDS_REQUIRE))
            } else {
                loginViewModel.login(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }
        }

        loginViewModel.result.observe(viewLifecycleOwner, Observer {
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        })

        binding.registerButton.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}