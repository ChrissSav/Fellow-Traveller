package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.ui.toPx

class PickNumberButton : ConstraintLayout {

    private var minusButtonSrc = 0
    private var plusButtonSrc = 0
    private var imagePadding = 5
    private var minusBtnDrawable: Drawable? = null
    private var plusBtnDrawable: Drawable? = null
    private var maxValue: Int? = null
    private var minValue: Int = 0
    private var pickTxtColor: Int = Color.WHITE
    private var pickTxtBackgroundTint: Drawable? = null
    private val pickNumberConstraintSet = ConstraintSet()
    private val textView = TextView(context).apply { id = View.generateViewId() }
    private val pickMinusImg = ImageView(context).apply { id = View.generateViewId() }
    private val pickPlusImg = ImageView(context).apply { id = View.generateViewId() }


    var pickButtonActionListener: PickButtonActionListener? = null
    var currentNum: Int = 0
        set(value) {
            field = value
            updateTextView()
        }

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
        addViews()
        createConstraintSet()
        setupAttributes(attrs)
        setupViews()
        setupListeners()
    }

    private fun addViews() {
        addView(pickMinusImg)
        addView(textView)
        addView(pickPlusImg)
    }

    private fun createConstraintSet() {

//        background = ContextCompat.getDrawable(context, R.drawable.increaser_border_stroke_shape_1)
        setPadding(25, 0, 25, 0)
        pickNumberConstraintSet.clone(this)
        pickNumberConstraintSet.apply {


            connect(pickMinusImg.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(pickMinusImg.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(pickMinusImg.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            constrainWidth(pickMinusImg.id, ViewGroup.LayoutParams.WRAP_CONTENT)
            constrainHeight(pickMinusImg.id, ViewGroup.LayoutParams.WRAP_CONTENT)


            connect(textView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(textView.id, ConstraintSet.END, pickPlusImg.id, ConstraintSet.END)
            connect(textView.id, ConstraintSet.START, pickMinusImg.id, ConstraintSet.START)
            connect(textView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            constrainWidth(textView.id, 35.toPx)
            constrainHeight(textView.id, 35.toPx)


            connect(pickPlusImg.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(pickPlusImg.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            connect(pickPlusImg.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            constrainWidth(pickPlusImg.id, ViewGroup.LayoutParams.WRAP_CONTENT)
            constrainHeight(pickPlusImg.id, ViewGroup.LayoutParams.WRAP_CONTENT)


            pickPlusImg.setPadding(imagePadding, imagePadding, imagePadding, imagePadding)
            pickMinusImg.setPadding(imagePadding, imagePadding, imagePadding, imagePadding)

        }.applyTo(this)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PickNumberButton)

            try {
                minusBtnDrawable = typedArray.getDrawable(R.styleable.PickNumberButton_mv_minusBtnDrawable)
                plusBtnDrawable = typedArray.getDrawable(R.styleable.PickNumberButton_mv_plusBtnDrawable)
                pickTxtColor = typedArray.getColor(R.styleable.PickNumberButton_mv_pickTxtColor, Color.WHITE)
                pickTxtBackgroundTint = typedArray.getDrawable(R.styleable.PickNumberButton_mv_pickTxtBackground)
                minValue = typedArray.getInt(R.styleable.PickNumberButton_minValue, 0)
                maxValue = typedArray.getInt(R.styleable.PickNumberButton_maxValue, -1)
                minusButtonSrc = typedArray.getResourceId(R.styleable.PickNumberButton_minus_src, R.drawable.ic_minus_1)
                plusButtonSrc = typedArray.getResourceId(R.styleable.PickNumberButton_plus_src, R.drawable.ic_plus)
                currentNum = minValue
            } catch (e: Exception) {
                e.printStackTrace()
            }

            typedArray.recycle()
        }
    }

    private fun setupViews() {
        pickNumberConstraintSet.applyTo(this)
        pickMinusImg.setImageResource(minusButtonSrc)
        pickPlusImg.setImageResource(plusButtonSrc)
        textView.setTextAppearance(this.context, R.style.input_number_picker)
        textView.text = minValue.toString()
        textView.gravity = Gravity.CENTER
        textView.setBackgroundResource(R.drawable.background_black_round)
    }

    private fun setupListeners() {
        pickMinusImg.setOnClickListener {
            decreasePick()

        }
        pickPlusImg.setOnClickListener {
            increasePick()
        }
    }

    private fun decreasePick() {
        currentNum--
        pickButtonActionListener?.onPickAction(currentNum)
    }

    private fun increasePick() {
        currentNum++
        pickButtonActionListener?.onPickAction(currentNum)
    }

    private fun updateTextView() {
        textView.text = currentNum.toString()
        pickMinusImg.isEnabled = currentNum != minValue
        pickPlusImg.isEnabled = currentNum != maxValue
    }

    fun setUpperLimit(limit: Int) {
        maxValue = limit
    }

    fun setLowerLimit(limit: Int) {
        minValue = limit
    }
}

interface PickButtonActionListener {
    fun onPickAction(value: Int)
}