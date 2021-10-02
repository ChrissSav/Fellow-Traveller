package gr.fellow.fellow_traveller.ui.home.settings

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentCarDetailsBinding
import gr.fellow.fellow_traveller.domain.AnswerType
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.ConfirmBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel

@AndroidEntryPoint
class CarDetailsFragment : BaseFragment<FragmentCarDetailsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private val args: CarDetailsFragmentArgs by navArgs()
    private lateinit var confirmBottomSheetDialog: ConfirmBottomSheetDialog


    override fun getViewBinding(): FragmentCarDetailsBinding =
        FragmentCarDetailsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.carDeletedId.observe(viewLifecycleOwner, Observer {
            onBackPressed()
        })
    }

    override fun setUpViews() {
        binding.brand.text = args.car.brand
        binding.model.text = args.car.model
        binding.plate.text = args.car.plate
        binding.color.text = args.car.color

        binding.back.button.setOnClickListener {
            onBackPressed()
        }



        binding.delete.setOnClickListener {
            confirmBottomSheetDialog = ConfirmBottomSheetDialog(
                getString(R.string.car_delete_confirmation_message, args.car.plate),
                this@CarDetailsFragment::onItemClickListener, 1
            )
            confirmBottomSheetDialog.show(childFragmentManager, "confirmBottomSheetDialog")
        }
    }


    private fun onItemClickListener(answerType: AnswerType) {
        if (answerType == AnswerType.Yes)
            viewModel.deleteCar(args.car)
    }


}