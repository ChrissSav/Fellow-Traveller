package gr.fellow.fellow_traveller.ui.home.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentUserMessengerBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel


@AndroidEntryPoint
class UserMessengerFragment : BaseFragment<FragmentUserMessengerBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getViewBinding(): FragmentUserMessengerBinding =
        FragmentUserMessengerBinding.inflate(layoutInflater)


    override fun setUpObservers() {

    }

    override fun setUpViews() {

    }


}