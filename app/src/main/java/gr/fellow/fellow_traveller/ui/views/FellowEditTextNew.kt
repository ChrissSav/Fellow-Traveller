package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FellowEditTextNewBinding
import gr.fellow.fellow_traveller.ui.extensions.getLength
import gr.fellow.fellow_traveller.ui.extensions.getString
import gr.fellow.fellow_traveller.ui.extensions.goneAnim
import gr.fellow.fellow_traveller.ui.extensions.visibleAnim
import gr.fellow.fellow_traveller.utils.isValidEmail
import gr.fellow.fellow_traveller.utils.isValidRegex


class FellowEditTextNew(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var hint = ""
    private var binding = FellowEditTextNewBinding.inflate(LayoutInflater.from(context), this, true)
    private var isEditable = false
    private var imeOptions = 0
    private var inputType = 0
    private var textAllCaps = true
    private var maxLength = 50
    private var regex = "^.{50}\$"
    private var label: String? = null
    private var errorMessage = ""
    private var correct = false
    private var passwordIsHide = false
    private var checkText = false
    var fellowEditTextNewActionListener: FellowEditTextNewActionListener? = null
    var fellowEditTextNewOnClickListener: FellowEditTextNewOnClickListener? = null

    fun setError(error: String) {
        binding.error.text = error
        binding.error.visibleAnim()
    }


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

        if (inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            binding.showHide.visibility = View.VISIBLE
            binding.editText.typeface = Typeface.DEFAULT
            binding.editText.transformationMethod = PasswordTransformationMethod()
        }

        if (textAllCaps)
            binding.editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS

        binding.editText.filters = arrayOf<InputFilter>(LengthFilter(maxLength))


        if (!isEditable) {
            binding.editText.keyListener = null
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

            override fun afterTextChanged(editable: Editable?) {
                fellowEditTextNewActionListener?.onTextChange(editable)
                if (checkText)
                    checkText()
            }

        })


        binding.showHide.setOnClickListener {
            if (passwordIsHide) {
                binding.editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.editText.transformationMethod = PasswordTransformationMethod()
                binding.showHide.setBackgroundResource(R.drawable.ic_eye_open)
            } else {
                binding.editText.inputType = InputType.TYPE_CLASS_TEXT
                binding.editText.transformationMethod = null
                binding.showHide.setBackgroundResource(R.drawable.ic_eye_closed)
            }
            binding.editText.setSelection(binding.editText.text.length)
            passwordIsHide = !passwordIsHide
        }

        binding.error.text = errorMessage


        binding.editText.setOnClickListener {
            fellowEditTextNewOnClickListener?.onClick()
        }
        binding.root.setOnClickListener {
            fellowEditTextNewOnClickListener?.onClick()
        }

    }

    private fun checkText() {
        val text = binding.editText.text.getString() ?: ""
        if (checkText)
            if (inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
                if (!isValidEmail(text)) {
                    Log.i("FellowEditText", "1")
                    binding.error.visibleAnim()
                    correct = false
                } else {
                    correct = true
                    binding.error.goneAnim()
                }
            } else if (!isValidRegex(text, regex)) {
                Log.i("FellowEditText", "2")
                binding.error.text = errorMessage
                binding.error.visibleAnim()
                correct = false
            } else {
                Log.i("FellowEditText", "3")
                correct = true
                binding.error.goneAnim()
            }
    }


    fun isCorrect(): Boolean {
        checkText = true
        checkText()
        return correct
    }


}


interface FellowEditTextNewOnClickListener {
    fun onClick()
}


interface FellowEditTextNewActionListener {
    fun onTextChange(value: Editable?)
}

