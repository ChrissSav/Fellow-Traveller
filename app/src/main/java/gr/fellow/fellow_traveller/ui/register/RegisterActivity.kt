package gr.fellow.fellow_traveller.ui.register

import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityRegisterBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.setTextHtml


@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {


    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var nav: NavController

    override fun provideViewBinding(): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.load.observe(this, {
            if (it)
                binding.genericLoader.root.visibility = View.VISIBLE
            else
                binding.genericLoader.root.visibility = View.INVISIBLE

        })


        viewModel.error.observe(this, {
            if (it.internal)
                createAlerter(getString(it.messageId))
            else
                createAlerter(it.message)

        })

        viewModel.email.observe(this, {
            binding.scroll.visibility = View.GONE
            binding.constraintLayoutConfirm.visibility = View.VISIBLE
            binding.emailConfirm.setTextHtml(getString(R.string.email_verify, it))

        })
    }

    override fun setUpViews() {

        binding.buttonRegister.setOnClickListener {
            if (binding.email.isCorrect() and binding.password.isCorrect() and binding.passwordConfirm.isCorrect() and binding.firstName.isCorrect() and binding.lastName.isCorrect()) {
                if (binding.password.text.toString() != binding.passwordConfirm.text.toString()) {
                    binding.passwordConfirm.setError(resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                } else {
                    val email = binding.email.text.toString()
                    val password = binding.password.text.toString()
                    val fistName = binding.firstName.text.toString()
                    val lastName = binding.lastName.text.toString()
                    viewModel.registerUser(fistName, lastName, email, password)
                }
            }
        }


        binding.ImageButtonBack.button.setOnClickListener {
            onBackPressed()
        }
    }


}