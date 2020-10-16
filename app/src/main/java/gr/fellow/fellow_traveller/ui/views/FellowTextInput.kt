package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FellowEdittextBinding

class FellowTextInput(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var hint = ""
    private var binding: FellowEdittextBinding = FellowEdittextBinding.inflate(LayoutInflater.from(context), this, true)
    private var isEditable = false
    private lateinit var function: () -> Unit
    private var imeOptions = 0
    private var inputType = 0

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

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FellowTextInput)
        try {
            hint = attributes.getString(R.styleable.FellowTextInput_hint_fellow).toString()
            isEditable = attributes.getBoolean(R.styleable.FellowTextInput_editable, false)
            inputType = attributes.getInteger(R.styleable.FellowTextInput_text_type, InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL)
            imeOptions = attributes.getInteger(R.styleable.FellowTextInput_imeOptions, EditorInfo.IME_ACTION_NEXT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        attributes.recycle()


        binding.fellowEditTextTextInputEditText.imeOptions = imeOptions

        binding.fellowEditTextTextInputEditText.inputType = inputType

        binding.fellowEditTextTextInputLayout.hint = hint

        if (!isEditable) {
            binding.fellowEditTextTextInputEditText.keyListener = null
            binding.fellowEditTextTextInputEditText.isClickable = false
            binding.fellowEditTextTextInputEditText.isFocusable = false
            binding.fellowEditTextTextInputEditText.isLongClickable = false
            binding.fellowEditTextTextInputEditText.isFocusableInTouchMode = false
            binding.fellowEditTextTextInputEditText.isCursorVisible = false

            binding.fellowEditTextTextInputLayout.isClickable = false
            binding.fellowEditTextTextInputLayout.isFocusable = false
            binding.fellowEditTextTextInputLayout.isLongClickable = false
            binding.fellowEditTextTextInputLayout.isFocusableInTouchMode = false

        }


        binding.fellowEditTextTextInputLayout.setOnClickListener {
            try {
                function.invoke()
            } catch (e: java.lang.Exception) {

            }
        }

        binding.fellowEditTextTextInputEditText.setOnClickListener {
            try {
                function.invoke()
            } catch (e: java.lang.Exception) {

            }
        }


    }

    fun onClickListener(function: () -> Unit) {
        this.function = function
    }


}





