package com.example.fellowtravellerbeta.ui.register.fragment.accountInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.register.fragment.RegisterSharedViewModel
import com.example.fellowtravellerbeta.utils.createSnackBar
import org.koin.android.ext.android.inject

class AccountInfoFragment : Fragment() {

    private lateinit var imageButtonBack: ImageButton
    private lateinit var imageButtonNext: ImageButton
    private val viewModel: RegisterSharedViewModel by inject()
    private var navController: NavController? = null
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private var access = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        editTextFirstName = view.findViewById(R.id.FragmentRegisterStage3_name_editText)
        editTextLastName = view.findViewById(R.id.FragmentRegisterStage3_surname_editText)

        imageButtonBack = view.findViewById(R.id.imageButton_back)
        imageButtonNext = view.findViewById(R.id.AccountInfoFragment_button_next)

        imageButtonBack.setOnClickListener {
            activity?.onBackPressed()
        }

        imageButtonNext.setOnClickListener {
            val firstName = editTextFirstName.text.toString()
            val lastName = editTextLastName.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                access = true
                viewModel.storeUserInfo(firstName, lastName)
            } else {
                createSnackBar(view, resources.getString(R.string.ERROR_FIELDS_REQUIRE))
            }

        }
        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            if(access){
                access = false
                if(it != null){

                }
            }
        })

    }


}