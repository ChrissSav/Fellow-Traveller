package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FellowEdittextBinding

class FellowTextInput(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var hint = ""
    private var binding: FellowEdittextBinding

    private lateinit var function: () -> Unit

    var text: String? = null
        get() = binding.fellowEditTextTextInputEditText.text.toString()
        set(value) {
            field = value
            binding.fellowEditTextTextInputEditText.setText(value)
        }

    var error: String? = null
        get() = binding.fellowEditTextTextInputLayout.error.toString()
        set(value) {
            field = value
            binding.fellowEditTextTextInputLayout.error = value
        }

    init {

        binding = FellowEdittextBinding.inflate(LayoutInflater.from(context), this, true)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FellowTextInput)
        try {
            hint = attributes.getString(R.styleable.FellowTextInput_hint_fellow).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.fellowEditTextTextInputLayout.hint = hint
        attributes.recycle()


        binding.fellowEditTextTextInputLayout.setOnClickListener {
            function.invoke()
        }

        binding.fellowEditTextTextInputEditText.setOnClickListener {
            function.invoke()
        }
    }

    fun onClickListener(function: () -> Unit) {
        this.function = function
    }
}





