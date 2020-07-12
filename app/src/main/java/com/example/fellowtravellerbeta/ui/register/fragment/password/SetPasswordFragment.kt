package com.example.fellowtravellerbeta.ui.register.fragment.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.register.fragment.RegisterSharedViewModel
import com.example.fellowtravellerbeta.utils.createSnackBar
import com.example.fellowtravellerbeta.utils.isValidEmail
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetPasswordFragment : Fragment() {


    private lateinit var imageButtonBack: ImageButton
    private lateinit var imageButtonNext: ImageButton
    private val viewModel: RegisterSharedViewModel by inject()
    private var navController: NavController? = null
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPasswordConfirm: EditText

    private var access = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        editTextPassword = view.findViewById(R.id.FragmentRegisterStage2_password_edit_text)
        editTextPasswordConfirm = view.findViewById(R.id.FragmentRegisterStage2_password2_edit_text)
        imageButtonNext = view.findViewById(R.id.SetPasswordFragment_button_next)
        imageButtonBack = view.findViewById(R.id.imageButton_back)


        editTextPassword.setText(viewModel.password.value)
        editTextPasswordConfirm.setText(viewModel.password.value)

        imageButtonBack.setOnClickListener {
            activity?.onBackPressed()
        }

        imageButtonNext.setOnClickListener {

            if (editTextPassword.text.length >= 8) {
                if (editTextPassword.text == editTextPasswordConfirm.text) {
                    viewModel.storePassword(editTextPassword.text.toString())
                } else {
                    createSnackBar(view, resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                }
            } else {
                createSnackBar(view, resources.getString(R.string.ERROR_PASSWORD_COMPLEXITY_LENGTH))

            }


        }

        viewModel.password.observe(viewLifecycleOwner, Observer {


            if (access) {
                access = false
                if (it != null) {
                    navController?.navigate(R.id.action_setPasswordFragment_to_accountInfoFragment)

                }
            }
        })

    }


}