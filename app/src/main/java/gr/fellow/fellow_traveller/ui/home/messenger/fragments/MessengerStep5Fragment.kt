package gr.fellow.fellow_traveller.ui.home.messenger.fragments

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentMessengerStep5Binding
import gr.fellow.fellow_traveller.ui.home.messenger.MessengerLinkActivity

class MessengerStep5Fragment : BaseFragment<FragmentMessengerStep5Binding>() {


    override fun getViewBinding(): FragmentMessengerStep5Binding =
        FragmentMessengerStep5Binding.inflate(layoutInflater)

    override fun setUpObservers() {
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun setUpViews() {


        binding.messengerLink.addTextChangedListener(object : TextWatcher {
            private var isEditing = false

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @SuppressLint("UseCompatLoadingForColorStateLists")
            override fun afterTextChanged(editable: Editable?) {

                if (!isEditing) {
                    isEditing = true

                    val sub = editable.toString().substringAfterLast(getString(R.string.messenger_url))
                    if (sub.isNotEmpty() && sub != editable.toString()) {
                        binding.messengerLink.setText(sub)
                        binding.done.visibility = View.VISIBLE
                    } else {
                        binding.done.visibility = View.GONE
                    }
                    isEditing = false
                }
            }

        })

        binding.done.setOnClickListener {
            (activity as MessengerLinkActivity).sendBackMessengerLink(binding.messengerLink.text.toString())
        }

    }

}