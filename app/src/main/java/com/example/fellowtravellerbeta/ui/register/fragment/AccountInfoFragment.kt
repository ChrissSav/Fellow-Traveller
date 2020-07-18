package com.example.fellowtravellerbeta.ui.register.fragment

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
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.register.RegisterSharedViewModel
import com.example.fellowtravellerbeta.utils.createSnackBar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AccountInfoFragment : Fragment() {

    private lateinit var imageButtonBack: ImageButton
    private lateinit var imageButtonNext: ImageButton
    private lateinit var imageButtonEraseFirstName: ImageButton
    private lateinit var imageButtonEraseLastName: ImageButton
    private val viewModel: RegisterSharedViewModel by sharedViewModel()
    private var navController: NavController? = null
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        editTextFirstName = view.findViewById(R.id.AccountInfoFragment_name_editText)
        editTextLastName = view.findViewById(R.id.AccountInfoFragment_surname_editText)

        imageButtonBack = view.findViewById(R.id.imageButton_back)
        imageButtonNext = view.findViewById(R.id.AccountInfoFragment_button_next)





        imageButtonEraseFirstName = view.findViewById(R.id.AccountInfoFragment_name_erase_button)
        imageButtonEraseLastName = view.findViewById(R.id.AccountInfoFragment_surname_erase_button)


        editTextFirstName.setText(viewModel.userInfo.value?.first)
        editTextLastName.setText(viewModel.userInfo.value?.second)


        editTextFirstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().isNotEmpty())
                    imageButtonEraseFirstName.visibility = View.VISIBLE
                else
                    imageButtonEraseFirstName.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        editTextLastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().isNotEmpty())
                    imageButtonEraseLastName.visibility = View.VISIBLE
                else
                    imageButtonEraseLastName.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        imageButtonEraseLastName.setOnClickListener {
            editTextLastName.text = null
        }

        imageButtonEraseFirstName.setOnClickListener {
            editTextFirstName.text = null
        }





        imageButtonBack.setOnClickListener {
            activity?.onBackPressed()
        }

        imageButtonNext.setOnClickListener {
            val firstName = editTextFirstName.text.toString()
            val lastName = editTextLastName.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                viewModel.storeUserInfo(firstName, lastName)



            } else {
                createSnackBar(view, resources.getString(R.string.ERROR_FIELDS_REQUIRE))
            }

        }


        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            val test = "Phone : ${ viewModel.phone.value.toString()} \nEmail : ${viewModel.email.value.toString()} \n Password: ${viewModel.password.value.toString()} \n F: ${viewModel.userInfo.value?.first.toString()} \nL : ${viewModel.userInfo.value?.second.toString()} \n"
            createSnackBar(view,test)
        })

        /*viewModel.userInfo.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                viewModel.registerUserAccount()
            }

        })*/


        viewModel.responseResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                200 -> createSnackBar(
                    view,
                    resources.getString(R.string.ERROR_PHONE_ALREADY_EXISTS)
                )
                201 -> createSnackBar(
                    view,
                    resources.getString(R.string.ERROR_EMAIL_ALREADY_EXISTS)
                )
                else -> createSnackBar(
                    view,
                    resources.getString(R.string.ERROR_API_UNREACHABLE)
                )

            }


        })

    }


}