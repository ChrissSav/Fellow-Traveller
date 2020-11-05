package gr.fellow.fellow_traveller.ui.user

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityUserInfoDetailsBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl

@AndroidEntryPoint
class UserInfoDetailsActivity : BaseActivity<ActivityUserInfoDetailsBinding>() {

    private val viewModel: UserInfoDetailsViewModel by viewModels()
    private var messengerLink: String? = null

    private var userId: String? = null

    override fun handleIntent() {
        userId = intent.getStringExtra("userId")
    }

    override fun provideViewBinding(): ActivityUserInfoDetailsBinding =
        ActivityUserInfoDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {


        viewModel.user.observe(this, Observer { user ->
            with(binding) {
                if (user.messengerLink.isNullOrEmpty())
                    messengerLink.visibility = View.INVISIBLE
                userImage.loadImageFromUrl(user.picture)
                userName.text = user.fullName
                reviews.text = user.reviews.toString()
                rate.text = user.rate.toString()
                involved.text = user.tripsInvolved.toString()
                offers.text = user.tripsOffers.toString()
                this@UserInfoDetailsActivity.messengerLink = user.messengerLink
                if (!user.aboutMe.isNullOrEmpty())
                    aboutMe.setText(user.aboutMe)
            }
        })

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
            viewModel.loadUserInfo(userId.toString())
        }


        binding.backButton.setOnClickListener {
            finish()
        }

        binding.messengerLink.setOnClickListener {
            messengerLink?.let {
                val uriUrl: Uri = Uri.parse(getString(R.string.messenger_link, it))
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                startActivity(launchBrowser)
            }

        }
    }


}