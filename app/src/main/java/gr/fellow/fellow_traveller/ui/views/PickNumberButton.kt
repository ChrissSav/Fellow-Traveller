package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.PickNumberButtonLayoutBinding

class PickNumberButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var minusButtonSrc = 0
    private var plusButtonSrc = 0
    private var imagePadding = 5
    private var minusBtnDrawable: Drawable? = null
    private var plusBtnDrawable: Drawable? = null
    private var maxValue: Int? = null
    private var minValue: Int = 0
    private var pickTxtColor: Int = Color.WHITE
    private var pickTxtBackgroundTint: Drawable? = null

    private var binding = PickNumberButtonLayoutBinding.inflate(LayoutInflater.from(context), this, true)


    var pickButtonActionListener: PickButtonActionListener? = null
    var currentNum: Int = 0
        set(value) {
            field = value
            updateTextView(value)
        }


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PickNumberButton)

        try {
            minusBtnDrawable = typedArray.getDrawable(R.styleable.PickNumberButton_mv_minusBtnDrawable)
            plusBtnDrawable = typedArray.getDrawable(R.styleable.PickNumberButton_mv_plusBtnDrawable)
            pickTxtColor = typedArray.getColor(R.styleable.PickNumberButton_mv_pickTxtColor, Color.WHITE)
            pickTxtBackgroundTint = typedArray.getDrawable(R.styleable.PickNumberButton_mv_pickTxtBackground)
            minValue = typedArray.getInt(R.styleable.PickNumberButton_minValue, 0)
            maxValue = typedArray.getInt(R.styleable.PickNumberButton_maxValue, 30)
            minusButtonSrc = typedArray.getResourceId(R.styleable.PickNumberButton_minus_src, R.drawable.ic_minus_1)
            plusButtonSrc = typedArray.getResourceId(R.styleable.PickNumberButton_plus_src, R.drawable.ic_plus)
            currentNum = minValue
        } catch (e: Exception) {
            e.printStackTrace()
        }

        typedArray.recycle()

        setupListeners()
    }


    private fun setupListeners() {
        binding.minus.setOnClickListener {
            decreasePick()

        }
        binding.plus.setOnClickListener {
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

    private fun updateTextView(currentNum: Int) {
        binding.label.text = currentNum.toString()
        binding.minus.isEnabled = currentNum != minValue
        binding.plus.isEnabled = currentNum != maxValue
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