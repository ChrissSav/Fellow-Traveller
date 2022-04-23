package gr.fellow.fellow_traveller.ui.search

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivitySearchTripBinding
import gr.fellow.fellow_traveller.domain.AnswerType
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.dialogs.ExitCustomDialog


@AndroidEntryPoint
class SearchTripActivity : BaseActivityViewModel<ActivitySearchTripBinding, SearchTripViewModel>(SearchTripViewModel::class.java) {


    private lateinit var nav: NavController
    private lateinit var localUser: LocalUser
    private lateinit var placeFrom: PlaceModel
    private lateinit var placeTo: PlaceModel

    companion object {
        @JvmStatic
        fun getInstance(localUser: LocalUser, placeFrom: PlaceModel, placeTo: PlaceModel): SearchTripActivity {
            val result = SearchTripActivity()
            result.localUser = localUser
            result.placeFrom = placeFrom
            result.placeTo = placeTo
            return result
        }
    }

    override fun provideViewBinding(): ActivitySearchTripBinding =
        ActivitySearchTripBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.load.observe(this, Observer {
            if (it)
                binding.genericLoader.root.visibility = View.VISIBLE
            else
                binding.genericLoader.root.visibility = View.INVISIBLE
        })
    }


    override fun setUpViews() {
        nav = Navigation.findNavController(this, R.id.SearchTripActivity_nav_host)
        viewModel.setDestinationFrom(placeFrom)
        viewModel.setDestinationTo(placeTo)
        viewModel.destinationsSet()
    }

    override fun onBackPressed() {

        if (viewModel.searchFilter.value?.latitudeFrom != null && viewModel.searchFilter.value?.latitudeTo != null)
            if (nav.currentDestination?.id == R.id.searchTripsFragment) {
                ExitCustomDialog(this, this::exitCustomDialogAnswerType, getString(R.string.cancel_search), 1).show(supportFragmentManager, "exitCustomDialog")
            } else {
                super.onBackPressed()
            }
        else {
            super.onBackPressed()
        }

    }

    private fun exitCustomDialogAnswerType(result: AnswerType) {
        if (result == AnswerType.Yes)
            finish()
    }
}