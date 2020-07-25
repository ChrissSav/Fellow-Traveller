package gr.fellow.fellow_traveller.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import gr.fellow.fellow_traveller.R


class ExitCustomDialog(
    private val activityOwner: Activity
) : AppCompatDialogFragment() {
    private lateinit var abortButton: Button
    private lateinit var stayButton: Button
    private lateinit var title: TextView
    private lateinit var label: TextView
    private lateinit var listener: ExitCustomDialogListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val dialog = Dialog(activityOwner, R.style.Theme_Dialog_Abort)
        val window = dialog.window
        window?.let {
            window.requestFeature(Window.FEATURE_NO_TITLE)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setContentView(R.layout.abort_dialog)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)




        abortButton = dialog.findViewById(R.id.abort_dialog_abort_button)
        stayButton = dialog.findViewById(R.id.abort_dialog_stay_button)

        title = dialog.findViewById(R.id.abort_dialog_label)
        label = dialog.findViewById(R.id.abort_dialog_info_textView)
        label.text = "Αν συνεχίσετε θα ακυρωθούν τα δεδομένα που καταχωρήσατε."
        title.text = "Απόρριψη της καταχώρησης ;"

        stayButton.setOnClickListener {
            listener.exitFrom(false)
        }

        abortButton.setOnClickListener {
            listener.exitFrom(true)
        }



        return dialog
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as ExitCustomDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString().toString() +
                        "must implement ExampleDialogListener"
            )
        }
    }

    interface ExitCustomDialogListener {
        fun exitFrom(result: Boolean)
    }
}