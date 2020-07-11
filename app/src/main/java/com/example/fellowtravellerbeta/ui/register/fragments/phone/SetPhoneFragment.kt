package com.example.fellowtravellerbeta.ui.register.fragments.phone

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.Observer
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.utils.createSnackBar
import com.example.fellowtravellerbeta.utils.isValidPhone
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetPhoneFragment : Fragment() {
    private val viewModel: SetPhoneViewModel by viewModel()

    private lateinit var imageButton: ImageButton
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



        editText = view.findViewById(R.id.SetPhoneFragment_number_editText)
        imageButton = view.findViewById(R.id.SetPhoneFragment_button_next)

        imageButton.setOnClickListener {
            if (isValidPhone(editText.text.toString())) {
                viewModel.checkUserPhone(editText.text.toString())
            } else {
                createSnackBar(view, resources.getString(R.string.INVALID_PHONE_FORMAT))
            }
        }

        viewModel.checkPhone.observe(viewLifecycleOwner, Observer {
            val t = it
            Log.i("makis", t.toString())
            if (it) {

            } else {
                createSnackBar(view, resources.getString(R.string.ERROR_PHONE_ALREADY_EXISTS))
            }
        })
    }
}