package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.LabelTextComponentBinding

class LabelTextComponent(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: LabelTextComponentBinding = LabelTextComponentBinding.inflate(LayoutInflater.from(context), this, true)

    var text: String = ""
        get() = binding.value.text.toString()
        set(value) {
            field = value
            binding.value.text = value
        }

    var label: String = ""
        get() = binding.label.text.toString()
        set(value) {
            field = value
            binding.label.text = value
        }

    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.LabelTextComponent)
        try {
            label = attributes.getString(R.styleable.LabelTextComponent_label_text).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        attributes.recycle()

    }


}

