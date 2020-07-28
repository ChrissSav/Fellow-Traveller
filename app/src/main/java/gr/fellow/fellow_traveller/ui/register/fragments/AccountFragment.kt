package gr.fellow.fellow_traveller.ui.register.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentAccountBinding
import gr.fellow.fellow_traveller.databinding.FragmentPhoneBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.home.main.HomeActivity
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel


class AccountFragment : Fragment() {

    private val registerViewModel: RegisterViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        binding.firstName.setText(registerViewModel.userInfo.value?.first)
        binding.lastName.setText(registerViewModel.userInfo.value?.second)



        binding.firstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().isNotEmpty())
                    binding.firstNameEraseButton.visibility = View.VISIBLE
                else
                    binding.firstNameEraseButton.visibility = View.INVISIBLE
                registerViewModel.storeUserInfo(
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString()
                )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.lastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().isNotEmpty())
                    binding.lastNameEraseButton.visibility = View.VISIBLE
                else
                    binding.lastNameEraseButton.visibility = View.INVISIBLE
                registerViewModel.storeUserInfo(
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString()
                )

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })



        binding.firstNameEraseButton.setOnClickListener {
            binding.firstName.text = null
        }


        binding.lastNameEraseButton.setOnClickListener {
            binding.lastName.text = null

        }

        binding.ImageButtonNext.setOnClickListener {
            registerViewModel.registerUser()
        }



        registerViewModel.error.observe(viewLifecycleOwner, Observer {
            createSnackBar(view, it)
        })


        registerViewModel.finish.observe(viewLifecycleOwner, Observer {

            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}