package gr.fellow.fellow_traveller.ui.user

import android.view.View
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivityUserInfoDetailsBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter

@AndroidEntryPoint
class UserProfileDetailsActivity : BaseActivityViewModel<ActivityUserInfoDetailsBinding, UserInfoDetailsViewModel>(UserInfoDetailsViewModel::class.java) {


   private var userId: String? = null

    override fun handleIntent() {
        userId = intent.getStringExtra("userId")
    }

    override fun provideViewBinding(): ActivityUserInfoDetailsBinding =
        ActivityUserInfoDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.load.observe(this, Observer {
            if (it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else
                binding.genericLoader.progressLoad.visibility = View.INVISIBLE

        })

        viewModel.error.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId))
            else
                createAlerter(it.message)
        })
    }

    override fun setUpViews() {

        if (userId == null) {
            finish()
        } else {
            viewModel.setUserId(userId.toString())
            viewModel.loadUserInfo()
        }

    }


}