package gr.fellow.fellow_traveller.ui.home.tabs

import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentNotificationsBinding


class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {


    override fun getViewBinding(): FragmentNotificationsBinding =
        FragmentNotificationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {}

    override fun setUpViews() {}


}