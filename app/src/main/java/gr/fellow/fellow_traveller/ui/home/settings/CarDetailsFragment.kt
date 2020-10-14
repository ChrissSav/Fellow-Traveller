package gr.fellow.fellow_traveller.ui.home.settings

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentCarDetailsBinding
import gr.fellow.fellow_traveller.domain.AnswerType
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.ConfirmBottomSheetDialog
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.onBackPressed

@AndroidEntryPoint
class CarDetailsFragment : BaseFragment<FragmentCarDetailsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var car: Car? = null
    private lateinit var confirmBottomSheetDialog: ConfirmBottomSheetDialog


    override fun getViewBinding(): FragmentCarDetailsBinding =
        FragmentCarDetailsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.carDeletedId.observe(viewLifecycleOwner, Observer {
            onBackPressed()
        })
    }

    override fun setUpViews() {
        car?.let {
            binding.brand.text = it.brand
            binding.model.text = it.model
            binding.plate.text = it.plate
            binding.color.text = it.color
        }

        binding.backButtons.setOnClickListener {
            onBackPressed()
        }

        binding.delete.setOnClickListener {
            confirmBottomSheetDialog = ConfirmBottomSheetDialog(
                getString(R.string.car_delete_confirmation_message, car?.plate),
                this@CarDetailsFragment::onItemClickListener
            )
            confirmBottomSheetDialog.show(childFragmentManager, "confirmBottomSheetDialog")
        }
    }


    private fun onItemClickListener(answerType: AnswerType) {
        if (answerType == AnswerType.Yes)
            car?.let { car -> viewModel.deleteCar(car) }
    }


    @Override
    override fun handleIntent() {
        car = requireArguments().getParcelable("car")
    }

}