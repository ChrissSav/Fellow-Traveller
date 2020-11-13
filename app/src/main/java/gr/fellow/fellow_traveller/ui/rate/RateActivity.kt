package gr.fellow.fellow_traveller.ui.rate

import android.view.View
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivityRateBinding
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl

@AndroidEntryPoint
class RateActivity : BaseActivityViewModel<ActivityRateBinding, RateViewModel>(RateViewModel::class.java) {

    private lateinit var notification: Notification


    override fun provideViewBinding(): ActivityRateBinding =
        ActivityRateBinding.inflate(layoutInflater)

    override fun handleIntent() {
        intent.getParcelableExtra<Notification>("notification")?.let {
            notification = it
        }
        if (!this::notification.isInitialized) {
            finish()
        }

    }

    override fun setUpObservers() {

        viewModel.success.observe(this, Observer {
            createToast("Η αξιολόγηση καταχωρήθηκε")
            finish()
        })

        viewModel.load.observe(this, Observer {
            if (it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else
                binding.genericLoader.progressLoad.visibility = View.INVISIBLE
        })

        viewModel.error.observe(this, Observer {
            if (it.internal) {
                createToast(getString(it.messageId))
                if (it.messageId == R.string.ERROR_REVIEW_CANT_REGISTER_THE_REVIEW)
                    finish()
            } else
                createAlerter(it.message)
        })


    }

    override fun setUpViews() {
        with(binding) {
            viewModel.checkReview(notification.user.id)


            rateName.text = notification.user.fullName
            rateImage.loadImageFromUrl(notification.user.picture)



            backButton.setOnClickListener {
                finish()
            }

            rate.setOnClickListener {

                if (rateBar.rating != 0f) {
                    viewModel.registerRate(notification.user.id, rateBar.rating)
                }

            }


            rateBar.setOnRatingChangeListener { _, rating, _ ->
                createToast("Η αξιολόγηση σας είναι: $rating")
            }


        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}