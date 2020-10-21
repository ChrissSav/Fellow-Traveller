package gr.fellow.fellow_traveller.ui.forgotPassword

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityForgotPasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.main.MainActivity

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>() {

    private val viewModel: ForgotPasswordViewModel by viewModels()


    override fun provideViewBinding(): ActivityForgotPasswordBinding =
        ActivityForgotPasswordBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        with(viewModel) {

            load.observe(this@ForgotPasswordActivity, Observer {
                if (it)
                    binding.genericLoader.progressLoad.visibility = View.VISIBLE
                else
                    binding.genericLoader.progressLoad.visibility = View.INVISIBLE
            })


            errorSecond.observe(this@ForgotPasswordActivity, Observer {
                if (it.internal)
                    createAlerter(getString(it.messageId))
                else
                    createAlerter(it.message)
            })


            successResetPassword.observe(this@ForgotPasswordActivity, Observer {
                val intent = Intent(this@ForgotPasswordActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("login", true)
                startActivity(intent)
                finishAffinity()
            })
        }
    }

    override fun setUpViews() {

    }


}