package com.example.fellowtravellerbeta.ui.register.fragment.phone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.fellowtravellerbeta.ui.register.RegisterSharedViewModel
import com.example.fellowtravellerbeta.utils.createSnackBar
import com.example.fellowtravellerbeta.utils.isValidPhone
import org.koin.android.ext.android.inject

class SetPhoneFragment : Fragment() {
    private val registerSharedViewModel: RegisterSharedViewModel by inject()


    private var navController: NavController? = null
    private lateinit var imageButtonBack: ImageButton

    private lateinit var eraseButton: ImageButton
    private lateinit var imageButtonNext: ImageButton
    private lateinit var editText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_phone, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        eraseButton = view.findViewById(R.id.SetPhoneFragment_erase_button)

        editText = view.findViewById(R.id.SetPhoneFragment_number_editText)
        imageButtonNext = view.findViewById(R.id.SetPhoneFragment_button_next)
        imageButtonBack = view.findViewById(R.id.imageButton_back)

        editText.setText(registerSharedViewModel.phone.value)


        imageButtonBack.setOnClickListener {
            activity?.onBackPressed()
        }
        eraseButton.setOnClickListener {
            editText.text = null
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty())
                    eraseButton.visibility = View.VISIBLE
                else
                    eraseButton.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        imageButtonNext.setOnClickListener {
            if (isValidPhone(editText.text.toString())) {
                registerSharedViewModel.checkUserPhone(editText.text.toString())
            } else {
                createSnackBar(view, resources.getString(R.string.INVALID_PHONE_FORMAT))
            }
        }

        registerSharedViewModel.phone.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                navController?.navigate(R.id.action_setPhoneFragment_to_setEmailFragment)

            } else {
                createSnackBar(view, resources.getString(R.string.ERROR_PHONE_ALREADY_EXISTS))
            }

        })
    }
}