package gr.fellow.fellow_traveller.ui.forgotPassword

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityForgotPasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.initializeBlur
import gr.fellow.fellow_traveller.ui.main.MainActivity

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>() {

    private val viewModel: ForgotPasswordViewModel by viewModels()


    override fun provideViewBinding(): ActivityForgotPasswordBinding =
        ActivityForgotPasswordBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        with(viewModel) {

            load.observe(this@ForgotPasswordActivity, {
                if (it)
                    binding.genericLoader.root.visibility = View.VISIBLE
                else
                    binding.genericLoader.root.visibility = View.INVISIBLE
            })


            error.observe(this@ForgotPasswordActivity, {
                if (it.internal)
                    createAlerter(getString(it.messageId))
                else
                    createAlerter(it.message)
            })


            successResetPassword.observe(this@ForgotPasswordActivity, {
                val intent = Intent(this@ForgotPasswordActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("login", true)
                startActivity(intent)
                finishAffinity()
            })
        }
    }

    override fun setUpViews() {

        initializeBlur(binding.genericLoader.blurView)

    }


}