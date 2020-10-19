package gr.fellow.fellow_traveller.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.getColor
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.ui.extensions.toDp
import gr.fellow.fellow_traveller.ui.extensions.toPx


class FellowEditText : ConstraintLayout {

    val DRAWABLE_RIGHT = 2
    private val inputTypes = mutableListOf<Pair<String, Int>>()

    private val ediTextConstraintSet = ConstraintSet()
    val editText = EditText(context).apply { id = View.generateViewId() }
    private val bottomLine = View(context).apply { id = View.generateViewId() }
    private val errorTextView = TextView(context).apply { id = View.generateViewId() }

    private var style: Int = 0
    private var drawableRight: Int = 0
    private var bottomLineColor: Int = 0
    private var bottomLineTopMargin: Float = 0F
    private var drawablePadding: Float = 0F
    private var padding_start: Float = 0F
    private var padding_end: Float = 0F
    private var hintTextString: String? = null
    private var inputType: String? = null


    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        initArray()
        addViews()
        createConstraintSet()
        setupAttributes(attrs)
        setupViews()
        setupListeners()
    }

    private fun addViews() {
        addView(editText)
        addView(bottomLine)
        //addView(errorTextView)
    }

    private fun createConstraintSet() {


        setPadding((-4).toPx, 0, 0, 0)
        ediTextConstraintSet.clone(this)
        ediTextConstraintSet.apply {

            connect(editText.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            connect(editText.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(editText.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

            connect(bottomLine.id, ConstraintSet.END, editText.id, ConstraintSet.END)
            connect(bottomLine.id, ConstraintSet.START, editText.id, ConstraintSet.START)
            connect(bottomLine.id, ConstraintSet.TOP, editText.id, ConstraintSet.BOTTOM)
            connect(bottomLine.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            constrainWidth(editText.id, ViewGroup.LayoutParams.MATCH_PARENT)
            constrainHeight(editText.id, 45.toPx)


            constrainWidth(bottomLine.id, ViewGroup.LayoutParams.MATCH_PARENT)
            constrainHeight(bottomLine.id, 12.toDp)


        }.applyTo(this)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FellowEditText)

            try {

                drawableRight = typedArray.getResourceId(R.styleable.FellowEditText_drawable_right, R.drawable.ic_close)
                bottomLineColor = typedArray.getColor(R.styleable.FellowEditText_bottom_line_color, getColor(this.context, R.color.softGrey_color))
                bottomLineTopMargin = typedArray.getDimension(R.styleable.FellowEditText_bottom_line_top_margin, 2.toPx.toFloat())
                drawablePadding = typedArray.getDimension(R.styleable.FellowEditText_drawable_padding, 0F)
                padding_start = typedArray.getDimension(R.styleable.FellowEditText_padding_start, 0F)
                padding_end = typedArray.getDimension(R.styleable.FellowEditText_padding_end, 0F)
                style = typedArray.getResourceId(R.styleable.FellowEditText_style, R.style.input)
                hintTextString = typedArray.getString(R.styleable.FellowEditText_hint_text)
                inputType = typedArray.getString(R.styleable.FellowEditText_input_type)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            typedArray.recycle()
        }


    }

    private fun setupViews() {
        ediTextConstraintSet.applyTo(this)
        editText.hint = hintTextString ?: ""
        editText.setTextAppearance(this.context, style)
        editText.isSingleLine = true
        editText.background = null
        try {
            editText.inputType = inputTypes.first { it.first == inputType ?: "text" }.second
        } catch (e: Exception) {
            editText.inputType = inputTypes[8].second
        }

        editText.filters = arrayOf<InputFilter>(LengthFilter(80))
        bottomLine.setBackgroundColor(bottomLineColor)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        editText.setOnTouchListener { _, event ->
            try {
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (editText.right - editText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                        editText.text.clear()
                    }
                }
            } catch (e: Exception) {

            }

            false
        }


        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().isNotEmpty())
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRight, 0)
                else
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }


    private fun initArray() {
        inputTypes.add(Pair("date", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_DATE))
        inputTypes.add(Pair("datetime", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_NORMAL))
        inputTypes.add(Pair("none", InputType.TYPE_NULL))
        inputTypes.add(Pair("number", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL))
        inputTypes.add(Pair("numberDecimal", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL))
        inputTypes.add(Pair("numberPassword", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD))
        inputTypes.add(Pair("numberSigned", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED))
        inputTypes.add(Pair("phone", InputType.TYPE_CLASS_PHONE))
        inputTypes.add(Pair("text", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL))
        inputTypes.add(Pair("textAutoComplete", InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE))
        inputTypes.add(Pair("textAutoCorrect", InputType.TYPE_TEXT_FLAG_AUTO_CORRECT))
        inputTypes.add(Pair("textCapCharacters", InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS))
        inputTypes.add(Pair("textCapSentences", InputType.TYPE_TEXT_FLAG_CAP_SENTENCES))
        inputTypes.add(Pair("textCapWords", InputType.TYPE_TEXT_FLAG_CAP_WORDS))
        inputTypes.add(Pair("textEmailAddress", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS))
        inputTypes.add(Pair("textEmailSubject", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT))
        inputTypes.add(Pair("textFilter", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_FILTER))
        inputTypes.add(Pair("textLongMessage", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE))
        inputTypes.add(Pair("textMultiLine", InputType.TYPE_TEXT_FLAG_MULTI_LINE))
        inputTypes.add(Pair("textNoSuggestions", InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS))
        inputTypes.add(Pair("textPassword", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD))
        inputTypes.add(Pair("textPersonName", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME))
        inputTypes.add(Pair("textPhonetic", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PHONETIC))
        inputTypes.add(Pair("textPostalAddress", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS))
        inputTypes.add(Pair("textShortMessage", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE))
        inputTypes.add(Pair("textUri", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI))
        inputTypes.add(Pair("textVisiblePassword", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD))
        inputTypes.add(Pair("textWebEditText", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT))
        inputTypes.add(Pair("textWebEmailAddress", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS))
        inputTypes.add(Pair("textWebPassword", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD))
        inputTypes.add(Pair("time", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_TIME))
    }

}
