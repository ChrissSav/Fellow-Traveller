package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import gr.fellow.fellow_traveller.R

class FellowTextInput(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var textInputLayout: TextInputLayout
    private var textInputEditText: TextInputEditText
    private var hint = ""

    private lateinit var function: () -> Unit

    var text: String? = null
        get() = textInputEditText.text.toString()
        set(value) {
            field = value
            textInputEditText.setText(value)
        }

    var error: String? = null
        get() = textInputLayout.error.toString()
        set(value) {
            field = value
            textInputLayout.error = value
        }

    init {
        inflate(context, R.layout.fellow_edittext, this)

        textInputLayout = findViewById(R.id.fellow_editText_textInputLayout)
        textInputEditText = findViewById(R.id.fellow_editText_TextInputEditText)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FellowTextInput)
        try {
            hint = attributes.getString(R.styleable.FellowTextInput_hint_fellow).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
        // textView.text = attributes.getString(R.styleable.BenefitView_text)
        textInputLayout.hint = hint
        attributes.recycle()


        textInputLayout.setOnClickListener {
            function.invoke()
        }

        textInputEditText.setOnClickListener {
            function.invoke()
        }
    }

    fun onClickListener(function: () -> Unit) {
        this.function = function
    }


}









