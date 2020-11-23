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
    private val listener: (AnswerType) -> Unit,
    private val red: Int
) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ConfirmBottomSheetDialogBinding.inflate(LayoutInflater.from(context), container, false)

        binding.title.text = title

        if (red == 1)
            activity?.getColor(R.color.red_color)?.let {
                binding.yes.setTextColor(it)
            }
        else
            activity?.getColor(R.color.red_color)?.let {
                binding.no.setTextColor(it)
            }


        binding.yes.setOnClickListener {
            listener(AnswerType.Yes)
            dismiss()
        }

        binding.no.setOnClickListener {
            listener(AnswerType.No)
            dismiss()
        }
        return binding.root.rootView
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog

    }


}