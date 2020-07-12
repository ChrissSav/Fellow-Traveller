package com.example.fellowtravellerbeta.ui.register.fragment.email

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


class SetEmailFragment : Fragment() {

    private val registerSharedViewModel: RegisterSharedViewModel by inject()
    private var navController: NavController? = null

    private lateinit var imageButtonBack: ImageButton

    private lateinit var imageButtonNext: ImageButton
    private lateinit var editTextEmail: EditText
    private var access = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_email, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        editTextEmail = view.findViewById(R.id.FragmentRegisterStage1_email_editText)
        imageButtonNext = view.findViewById(R.id.SetEmailFragment_button_next)
        imageButtonBack = view.findViewById(R.id.imageButton_back)


        editTextEmail.setText(registerSharedViewModel.email.value)

        imageButtonBack.setOnClickListener {
            activity?.onBackPressed()
        }

        imageButtonNext.setOnClickListener {
            if (isValidEmail(editTextEmail.text.toString())) {
                access = true

                registerSharedViewModel.checkUserEmail(editTextEmail.text.toString())
            } else {
                createSnackBar(view, resources.getString(R.string.ERROR_INVALID_EMAIL_FORMAT))
            }
        }

        registerSharedViewModel.email.observe(viewLifecycleOwner, Observer {


            if (access) {
                access = false
                if (it != null) {
                    navController?.navigate(R.id.action_setEmailFragment_to_setPasswordFragment)

                } else {
                    createSnackBar(view, resources.getString(R.string.ERROR_EMAIL_ALREADY_EXISTS))
                }

            }
        })

    }
}