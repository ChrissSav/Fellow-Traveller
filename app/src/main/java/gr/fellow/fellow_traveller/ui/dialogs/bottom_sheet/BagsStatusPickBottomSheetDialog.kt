package gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.BagsStatusBottomSheetDialogBinding
import gr.fellow.fellow_traveller.domain.BagsStatusType

class BagsStatusPickBottomSheetDialog(
    private val onBagsItemClickListener: (BagsStatusType) -> Unit
) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = BagsStatusBottomSheetDialogBinding.inflate(LayoutInflater.from(context), container, false)


        binding.none.setOnClickListener {
            onBagsItemClickListener(BagsStatusType.NONE)
            dismiss()
        }
        binding.limited.setOnClickListener {
            onBagsItemClickListener(BagsStatusType.LIMITED)
            dismiss()
        }
        binding.large.setOnClickListener {
            onBagsItemClickListener(BagsStatusType.LARGE)
            dismiss()
        }
        return binding.root.rootView
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }


}