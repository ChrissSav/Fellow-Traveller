package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FellowTextInputBinding


class FellowTextInput(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var hint = ""
    private var binding = FellowTextInputBinding.inflate(LayoutInflater.from(context), this, true)
    private var isEditable = false
    private lateinit var function: () -> Unit
    private var imeOptions = 0
    private var inputType = 0
    private var maxLength = 100
    private var textAllCaps = true

    var text: String? = null
        get() {
            return if (binding.fellowEditTextTextInputEditText.text.isNullOrEmpty()) {
                null
            } else {
                binding.fellowEditTextTextInputEditText.text.toString()
            }
        }
        set(value) {
            field = value
            binding.fellowEditTextTextInputEditText.setText(value)
        }

    var length: Int = 0
        get() {
            return if (binding.fellowEditTextTextInputEditText.text.isNullOrEmpty()) {
                0
            } else {
                binding.fellowEditTextTextInputEditText.text.toString().length
            }
        }
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
            maxLength = attributes.getInteger(R.styleable.FellowTextInput_max_length, 100)
            textAllCaps = attributes.getBoolean(R.styleable.FellowTextInput_all_caps, false)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        attributes.recycle()


        binding.fellowEditTextTextInputEditText.imeOptions = imeOptions

        binding.fellowEditTextTextInputEditText.inputType = inputType

        binding.fellowEditTextTextInputLayout.hint = hint

        if (textAllCaps)
            binding.fellowEditTextTextInputEditText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS

        binding.fellowEditTextTextInputEditText.filters = arrayOf<InputFilter>(LengthFilter(maxLength))


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
            } catch (e: Exception) {
                println("fellowEditTextTextInputLayout Exception")
                e.printStackTrace()
            }
        }

        binding.fellowEditTextTextInputEditText.setOnClickListener {
            try {
                function.invoke()
            } catch (e: Exception) {
                println("fellowEditTextTextInputEditText Exception")
                e.printStackTrace()
            }
        }


    }

    fun onClickListener(function: () -> Unit) {
        this.function = function
    }


}





