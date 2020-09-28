package gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.PetBottomSheetDialogBinding
import gr.fellow.fellow_traveller.domain.PetAnswerType

class PetBottomSheetDialog(
    private val onItemClickListener: (PetAnswerType) -> Unit
) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = PetBottomSheetDialogBinding.inflate(LayoutInflater.from(context), container, false)
        binding.yes.setOnClickListener {
            onItemClickListener(PetAnswerType.Yes)
            dismiss()
        }
        binding.no.setOnClickListener {
            onItemClickListener(PetAnswerType.No)
            dismiss()
        }
        return binding.root.rootView
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog

    }


}