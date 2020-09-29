package gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ConfirmBottomSheetDialogBinding
import gr.fellow.fellow_traveller.domain.AnswerType

class ConfirmBottomSheetDialog(
    private val title: String,
    private val onItemClickListener: (AnswerType) -> Unit
) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ConfirmBottomSheetDialogBinding.inflate(LayoutInflater.from(context), container, false)

        binding.title.text = title

        binding.yes.setOnClickListener {
            onItemClickListener(AnswerType.Yes)
            dismiss()
        }
        binding.no.setOnClickListener {
            onItemClickListener(AnswerType.No)
            dismiss()
        }
        return binding.root.rootView
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog

    }


}