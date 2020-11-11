package gr.fellow.fellow_traveller.ui.rate

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.willy.ratingbar.BaseRatingBar.OnRatingChangeListener
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityRateBinding
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl

@AndroidEntryPoint
class RateActivity : BaseActivity<ActivityRateBinding>() {

    private val viewModel: RateViewModel by viewModels()
    private var rating: Float = 0f

    override fun provideViewBinding(): ActivityRateBinding =
        ActivityRateBinding.inflate(layoutInflater)

    //We do here all our put extras
    override fun handleIntent() {

    }

    override fun setUpObservers() {

        viewModel.success.observe(this, Observer {
            finish()
        })

    }

    override fun setUpViews() {
        with(binding) {

            backButton.setOnClickListener { finish() }

            rateAddButton.setOnClickListener {

                if (rating != 0f) {
                    val comment = rateInput.text.toString().trim()
                    viewModel.registerRate("1", comment, rating)
                    createToast("Επιτυχής αξιολόγηση")
                } else {
                    createToast("Συμπληρώστε όλα τα απαραίτητα πεδία")
                }

            }

            //val userName: String? = intent.getStringExtra("userName")
            rateName.text = "Tyler M. Joseph"


            //gliding the image
            val url =
                "https://firebasestorage.googleapis.com/v0/b/fellowtraveller-360a8.appspot.com/o/profile_images%2F128.jpg?alt=media&token=72364680-b2ce-4965-ab0c-b26ad330a15c"
            rateImage.loadImageFromUrl(url)

            rateBar.setOnRatingChangeListener(OnRatingChangeListener { ratingBar, rating, fromUser ->
                createToast("Η αξιολόγηση σας είναι: $rating")
            })

        }
    }

    private fun validate(rate: Float): Boolean {
        return (rate.toInt() != 0)
    }
}