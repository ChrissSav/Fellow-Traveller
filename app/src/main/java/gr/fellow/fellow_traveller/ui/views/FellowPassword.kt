package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FellowPasswordBinding

class FellowPassword(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var hint = ""
    private var binding = FellowPasswordBinding.inflate(LayoutInflater.from(context), this, true)
    private var imeOptions = 0
    private var passwordIsHide = true

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

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FellowPassword)
        try {
            hint = attributes.getString(R.styleable.FellowPassword_hint_Password).toString()
            imeOptions = attributes.getInteger(R.styleable.FellowPassword_imeOptions_Password, EditorInfo.IME_ACTION_NEXT)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        binding.fellowEditTextTextInputLayout.hint = hint
        binding.fellowEditTextTextInputEditText.imeOptions = imeOptions
        binding.fellowEditTextTextInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.fellowEditTextTextInputEditText.transformationMethod = PasswordTransformationMethod()

        attributes.recycle()


        binding.showHide.setOnClickListener {
            if (passwordIsHide) {
                binding.fellowEditTextTextInputEditText.transformationMethod = PasswordTransformationMethod()
                binding.showHide.text = this@FellowPassword.context.getString(R.string.show_password)
            } else {
                binding.fellowEditTextTextInputEditText.transformationMethod = null
                binding.showHide.text = this@FellowPassword.context.getString(R.string.hide_password)

            }
            binding.fellowEditTextTextInputEditText.setSelection(binding.fellowEditTextTextInputEditText.text.toString().length)
            passwordIsHide = !passwordIsHide
        }

    }


}





