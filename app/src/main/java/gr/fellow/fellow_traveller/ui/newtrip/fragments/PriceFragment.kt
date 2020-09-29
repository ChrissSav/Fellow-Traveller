package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentPriceBinding
import gr.fellow.fellow_traveller.ui.findNavController
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel

@AndroidEntryPoint
class PriceFragment : BaseFragment<FragmentPriceBinding>() {
    private val viewModel: NewTripViewModel by activityViewModels()


    override fun getViewBinding(): FragmentPriceBinding =
        FragmentPriceBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.price.observe(viewLifecycleOwner, Observer {
            binding.AddPriceFragmentEditTextPrice.setText(it.toString())
            updateTotal(it)
        })

        updateTotal(viewModel.price.value ?: 0.toFloat())
    }

    override fun setUpViews() {
        binding.ImageButtonNext.root.setOnClickListener {
            findNavController()?.navigate(R.id.next_fragment)
        }


        binding.AddPriceFragmentEditTextPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                var price: Float = "0".toFloat()
                if (charSequence.toString().trim().isNotEmpty())
                    price = binding.AddPriceFragmentEditTextPrice.text.toString().toFloat()
                updateTotal(price)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    @SuppressLint("StringFormatMatches")
    private fun updateTotal(price: Float) {
        binding.priceTotal.text = getString(R.string.price, (price * viewModel.seats.value!!).toString())
        binding.priceAnalytically.text = getString(R.string.fragment_price_overview, viewModel.seats.value.toString(), price.toString())
    }


    override fun onDestroy() {
        super.onDestroy()
        if (binding.AddPriceFragmentEditTextPrice.text.toString().trim().isNotEmpty())
            viewModel.setPrice(binding.AddPriceFragmentEditTextPrice.text.toString().toFloat())
    }

}