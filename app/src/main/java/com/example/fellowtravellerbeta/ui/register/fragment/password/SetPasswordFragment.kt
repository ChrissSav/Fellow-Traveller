package com.example.fellowtravellerbeta.ui.register.fragment.password

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.register.RegisterSharedViewModel
import com.example.fellowtravellerbeta.utils.createSnackBar
import org.koin.android.ext.android.inject


class SetPasswordFragment : Fragment() {


    private lateinit var imageButtonBack: ImageButton
    private lateinit var imageButtonNext: ImageButton
    private val viewModel: RegisterSharedViewModel by inject()
    private var navController: NavController? = null
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPasswordConfirm: EditText
    private lateinit var passwordShowButton: Button
    private lateinit var confirmPasswordShowButton: Button


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


        editTextPassword = view.findViewById(R.id.SetPasswordFragment_password_edit_text)
        editTextPasswordConfirm = view.findViewById(R.id.SetPasswordFragment_password2_edit_text)
        imageButtonNext = view.findViewById(R.id.SetPasswordFragment_button_next)
        imageButtonBack = view.findViewById(R.id.imageButton_back)
        passwordShowButton = view.findViewById(R.id.SetPasswordFragment_display_password_button)
        confirmPasswordShowButton =view.findViewById(R.id.SetPasswordFragment_display_password2_button)




        editTextPassword.setText(viewModel.password.value)
        editTextPasswordConfirm.setText(viewModel.password.value)

        imageButtonBack.setOnClickListener {
            activity?.onBackPressed()
        }

        imageButtonNext.setOnClickListener {

            if (editTextPassword.text.length >= 8) {
                if (editTextPassword.text.toString() == editTextPasswordConfirm.text.toString()) {
                    viewModel.storePassword(editTextPassword.text.toString())
                } else {
                    createSnackBar(view, resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                }
            } else {
                createSnackBar(view, resources.getString(R.string.ERROR_PASSWORD_COMPLEXITY_LENGTH))

            }


        }


        editTextPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().isNotEmpty())
                    confirmPasswordShowButton.visibility = View.VISIBLE
                else
                    confirmPasswordShowButton.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().isNotEmpty())
                    passwordShowButton.visibility = View.VISIBLE
                else
                    passwordShowButton.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })




        confirmPasswordShowButton.setOnClickListener {
            if (editTextPasswordConfirm.inputType != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                editTextPasswordConfirm.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            } else {
                editTextPasswordConfirm.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD


            }
            editTextPasswordConfirm.setSelection(editTextPasswordConfirm.length())

        }

        passwordShowButton.setOnClickListener {
            if (editTextPassword.inputType != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                editTextPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                editTextPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD


            }
            editTextPassword.setSelection(editTextPassword.length())

        }




        viewModel.password.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                navController?.navigate(R.id.action_setPasswordFragment_to_accountInfoFragment)
            }

        })

    }


}