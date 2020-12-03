package gr.fellow.fellow_traveller.ui.auth

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityVerifyAccountBinding
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.startActivityClearStack
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainActivity

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

        viewModel.load.observe(this, Observer {
            if (it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else
                binding.genericLoader.progressLoad.visibility = View.INVISIBLE

        })

        viewModel.error.observe(this, Observer {
            if (it.internal)
                createToast(getString(it.messageId))
            else
                createToast(it.message)
            finish()
        })

        viewModel.success.observe(this, Observer {
            createToast(getString(R.string.authentication_success))
            startActivityClearStack(HomeActivity::class)
        })


        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("login", true)
            startActivity(intent)
            finishAffinity()
        }

    }

    override fun setUpViews() {
        viewModel.verify(token.toString())
    }


}