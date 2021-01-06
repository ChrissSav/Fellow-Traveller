package gr.fellow.fellow_traveller.ui.auth

import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityVerifyAccountBinding
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.initializeBlur
import gr.fellow.fellow_traveller.ui.extensions.startActivityClearStack
import gr.fellow.fellow_traveller.ui.home.HomeActivity

@AndroidEntryPoint
class VerifyAccountActivity : BaseActivity<ActivityVerifyAccountBinding>() {

    private val viewModel: VerifyAccountViewModel by viewModels()
    private var token: String? = null

    override fun provideViewBinding(): ActivityVerifyAccountBinding =
        ActivityVerifyAccountBinding.inflate(layoutInflater)


    override fun handleIntent() {
        val data = intent.data
        try {
            token = data?.getQueryParameter("token")
        } catch (ex: Exception) {

        }
    }


    override fun setUpObservers() {

        viewModel.load.observe(this, {
            if (it)
                binding.genericLoader.root.visibility = View.VISIBLE
            else
                binding.genericLoader.root.visibility = View.INVISIBLE

        })

        viewModel.error.observe(this, {
            if (it.internal)
                createToast(getString(it.messageId))
            else {
                createToast(it.message)
            }
            finish()
        })

        viewModel.success.observe(this, {
            createToast(getString(R.string.authentication_success))
            startActivityClearStack(HomeActivity::class)
        })


    }


    override fun setUpViews() {
        viewModel.verify(token.toString())

        initializeBlur(binding.genericLoader.blurView)

    }


}