package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FellowEditTextBinding
import gr.fellow.fellow_traveller.ui.extensions.getLength
import gr.fellow.fellow_traveller.ui.extensions.getString
import gr.fellow.fellow_traveller.utils.isValidEmail
import gr.fellow.fellow_traveller.utils.isValidRegex


class FellowEditText(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var hint = ""
    private var binding = FellowEditTextBinding.inflate(LayoutInflater.from(context), this, true)
    private var isEditable = false
    private lateinit var function: () -> Unit
    private var imeOptions = 0
    private var inputType = 0
    private var textAllCaps = true
    private var maxLength = 50
    private var regex = "^.{50}\$"
    private var label: String? = null
    private var errorMessage = ""
    private var correct = false

    var text: String? = null
        get() = binding.editText.text.getString()
        set(value) {
            field = value
            binding.editText.setText(value)
        }

    var length: Int = 0
        get() = binding.editText.text.getLength()
        set(value) {
            field = value
            binding.editText.setText(value)
        }


    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FellowEditText)
        try {
            hint = attributes.getString(R.styleable.FellowEditText_hint_f).toString()
            isEditable = attributes.getBoolean(R.styleable.FellowEditText_editable_f, true)
            inputType = attributes.getInteger(R.styleable.FellowEditText_text_type_f, InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            imeOptions = attributes.getInteger(R.styleable.FellowEditText_imeOptions_f, EditorInfo.IME_ACTION_NEXT)
            textAllCaps = attributes.getBoolean(R.styleable.FellowEditText_all_caps_f, false)
            maxLength = attributes.getInteger(R.styleable.FellowEditText_max_length_f, 50)
            regex = attributes.getString(R.styleable.FellowEditText_regex_f) ?: resources.getString(R.string.regex_accept_all)
            errorMessage = attributes.getString(R.styleable.FellowEditText_error_message_f) ?: resources.getString(R.string.check_field)
            label = attributes.getString(R.styleable.FellowEditText_label_f)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        attributes.recycle()


        binding.editText.imeOptions = imeOptions
        binding.editText.inputType = inputType
        binding.editText.hint = hint

        if (textAllCaps)
            binding.editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS

        binding.editText.filters = arrayOf<InputFilter>(LengthFilter(maxLength))


        if (!isEditable) {
            binding.editText.keyListener = null
            binding.editText.isClickable = false
            binding.editText.isFocusable = false
            binding.editText.isLongClickable = false
            binding.editText.isFocusableInTouchMode = false
            binding.editText.isCursorVisible = false
        }


        binding.editText.hint = hint
        binding.label.text = hint

        label?.let {
            binding.label.text = it
        }



        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val text = p0?.getString() ?: ""
                if (inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
                    if (!isValidEmail(text)) {
                        Log.i("FellowEditText", "1")
                        binding.error.text = errorMessage
                        correct = false
                    } else {
                        correct = true
                        binding.error.text = null

                    }
                } else if (!isValidRegex(text, regex)) {
                    Log.i("FellowEditText", "2")
                    binding.error.text = errorMessage
                    correct = false
                } else {
                    correct = true
                    binding.error.text = null
                }
            }

        })

        binding.editText.setOnClickListener {
            try {
                function.invoke()
            } catch (e: Exception) {
                println("fellowEditTextTextInputLayout Exception")
                e.printStackTrace()
            }
        }

        binding.root.setOnClickListener {
            try {
                function.invoke()
            } catch (e: Exception) {
                println("fellowEditTextTextInputEditText Exception")
                e.printStackTrace()
            }
        }


    }

    fun addOnClickListener(function: () -> Unit) {
        this.function = function
    }

    fun isCorrect() = correct


}





