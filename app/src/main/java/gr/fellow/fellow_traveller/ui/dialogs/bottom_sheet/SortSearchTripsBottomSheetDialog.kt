package gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ConfirmBottomSheetDialogBinding
import gr.fellow.fellow_traveller.databinding.SortSearchTripsSheetDialogBinding
import gr.fellow.fellow_traveller.domain.PetAnswerType
import gr.fellow.fellow_traveller.domain.SortAnswerType

class SortSearchTripsBottomSheetDialog(
    private val onItemClickListener: (SortAnswerType) -> Unit
) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = SortSearchTripsSheetDialogBinding.inflate(LayoutInflater.from(context), container, false)

        binding.title.text = getString(R.string.title_sort_by)

        binding.relevant.setOnClickListener {
            onItemClickListener(SortAnswerType.Relevant)
            dismiss()
        }
        binding.price.setOnClickListener {
            onItemClickListener(SortAnswerType.Price)
            dismiss()
        }
        binding.rate.setOnClickListener {
            onItemClickListener(SortAnswerType.Rate)
            dismiss()
        }
        return binding.root.rootView
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog

    }


}