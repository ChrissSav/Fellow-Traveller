package gr.fellow.fellow_traveller.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import gr.fellow.fellow_traveller.R

class TripBookConfirmDialog(
    private val activityOwner: Activity,
    private val listener: (Boolean) -> Unit
) : AppCompatDialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val dialog = Dialog(activityOwner, R.style.Theme_Dialog_Abort)
        val window = dialog.window
        window?.let {
            window.requestFeature(Window.FEATURE_NO_TITLE)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setContentView(R.layout.delete_confirmation_dialog)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        val abortButton = dialog.findViewById<Button>(R.id.abort_dialog_abort_button)
        val stayButton = dialog.findViewById<Button>(R.id.abort_dialog_stay_button)

        val title = dialog.findViewById<TextView>(R.id.abort_dialog_label)
        val label = dialog.findViewById<TextView>(R.id.abort_dialog_info_textView)
        label.text = "Η κίνηση αυτή είναι αμετάκλιτη"
        title.text = "Κράτηση Ταξιδοού"

        stayButton.setOnClickListener {
            listener(true)
        }

        abortButton.setOnClickListener {
            listener(false)
        }



        return dialog
    }
}