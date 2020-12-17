package gr.fellow.fellow_traveller.ui.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentHomeBinding
import gr.fellow.fellow_traveller.databinding.FragmentMessengerBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import java.util.Observer



class MessengerFragment : BaseFragment<FragmentMessengerBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentMessengerBinding =
        FragmentMessengerBinding.inflate(layoutInflater)



    override fun setUpObservers() {

    }

    override fun setUpViews() {

    }





}

