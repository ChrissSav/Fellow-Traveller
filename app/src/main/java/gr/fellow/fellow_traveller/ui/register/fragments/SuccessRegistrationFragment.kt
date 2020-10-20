package gr.fellow.fellow_traveller.ui.register.fragments


import android.content.Intent
import android.os.Handler
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSuccessRegistrationBinding
import gr.fellow.fellow_traveller.ui.extensions.setTextHtml
import gr.fellow.fellow_traveller.ui.extensions.startAnimation
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel


@AndroidEntryPoint
class SuccessRegistrationFragment : BaseFragment<FragmentSuccessRegistrationBinding>() {

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding(): FragmentSuccessRegistrationBinding =
        FragmentSuccessRegistrationBinding.inflate(layoutInflater)

    override fun setUpObservers() {}

    override fun setUpViews() {

        binding.email.setTextHtml(getString(R.string.email_verify, viewModel.email.value.toString()))


        Handler().postDelayed({
            binding.view3.startAnimation()

        }, 300.toLong())


        binding.openEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }
}
