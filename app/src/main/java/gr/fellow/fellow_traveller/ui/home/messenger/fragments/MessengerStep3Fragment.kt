package gr.fellow.fellow_traveller.ui.home.messenger.fragments

import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentMessengerStep3Binding
import java.util.*

class MessengerStep3Fragment : BaseFragment<FragmentMessengerStep3Binding>() {

    override fun getViewBinding(): FragmentMessengerStep3Binding =
        FragmentMessengerStep3Binding.inflate(layoutInflater)

    override fun setUpObservers() {
    }

    override fun setUpViews() {

        if (Locale.getDefault().language.equals("el"))
            binding.imageView5.setImageResource(R.drawable.ic_messenger_4)
        else
            binding.imageView5.setImageResource(R.drawable.ic_messenger_4)

    }

}