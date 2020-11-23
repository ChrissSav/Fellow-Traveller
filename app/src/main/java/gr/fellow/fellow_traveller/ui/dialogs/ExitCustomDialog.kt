package gr.fellow.fellow_traveller.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.AbortDialogBinding
import gr.fellow.fellow_traveller.domain.AnswerType


class ExitCustomDialog(
    private val activityOwner: Activity,
    private val listener: (AnswerType) -> Unit,
    private val title: String,
    private val red: Int
) : AppCompatDialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = AbortDialogBinding.inflate(layoutInflater)

        val dialog = Dialog(activityOwner, R.style.Theme_Dialog_Abort)
        val window = dialog.window
        window?.let {
            window.requestFeature(Window.FEATURE_NO_TITLE)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        if (red != 1)
            activity?.getColor(R.color.red_color)?.let {
                binding.exit.setTextColor(it)
            }


        binding.title.text = title


        binding.exit.setOnClickListener {
            listener.invoke(AnswerType.Yes)
            dismiss()
        }

        binding.stay.setOnClickListener {
            listener.invoke(AnswerType.No)
            dismiss()
        }

        return dialog
    }
}