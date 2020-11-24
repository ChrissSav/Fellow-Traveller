package gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.UserImageBottomSheetDialogBinding

class UserImagePickBottomSheetDialog(
    private val onItemClickListener: (Boolean) -> Unit
) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binding = UserImageBottomSheetDialogBinding.inflate(LayoutInflater.from(context), container, false)


        binding.upload.setOnClickListener {
            onItemClickListener(true)
            dismiss()
        }
        binding.delete.setOnClickListener {
            onItemClickListener(false)
            dismiss()
        }
        return binding.root.rootView
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }


}