package gr.fellow.fellow_traveller.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.DisplayUserPictureDialogBinding
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl


class DisplayUserPictureDialog(
    private val activityOwner: Activity,
    private val url: String,
) : AppCompatDialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DisplayUserPictureDialogBinding.inflate(layoutInflater)

        val dialog = Dialog(activityOwner, R.style.Theme_Dialog_Abort)
        val window = dialog.window
        window?.let {
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        binding.picture.loadImageFromUrl(url)

        binding.picture.setOnClickListener {
            dialog.dismiss()
        }

        binding.root.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }
}