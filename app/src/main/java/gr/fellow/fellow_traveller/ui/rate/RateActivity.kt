package gr.fellow.fellow_traveller.ui.rate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityRateBinding
import gr.fellow.fellow_traveller.databinding.ActivityRegisterBinding

class RateActivity : BaseActivity<ActivityRateBinding>() {

    override fun provideViewBinding(): ActivityRateBinding =
        ActivityRateBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        TODO("Not yet implemented")
    }

    override fun setUpViews() {
        binding.mytext.text = "Hello boy!"
    }

}