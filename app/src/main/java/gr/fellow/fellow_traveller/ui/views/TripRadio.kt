package gr.fellow.fellow_traveller.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.TripRadioLayoutBinding
import gr.fellow.fellow_traveller.ui.extensions.then


class TripRadio(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = TripRadioLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    var onClick: TripRadioOnClickListener? = null

    var type: TripRad = TripRad.OFFER

    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TripRadio)
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
        attributes.recycle()

        onChange(TripRad.OFFER)

        binding.search.setOnClickListener {
            onChange(TripRad.SEARCH)
        }

        binding.offer.setOnClickListener {
            onChange(TripRad.OFFER)
        }
    }

    private fun onChange(typeT: TripRad) {
        type = typeT
        onClick?.onClick(type)
        if (type == TripRad.OFFER) {
            setOffer(true)
            setSearch(false)
        } else {
            setOffer(false)
            setSearch(true)
        }
    }


    private fun setOffer(check: Boolean) {
        val backgroundTint = check then { resources.getColorStateList(R.color.green_60_new, null) } ?: resources.getColorStateList(R.color.black_new, null)
        val textColor = check then { ContextCompat.getColor(context, R.color.green_new) } ?: ContextCompat.getColor(context, R.color.white_60_new)

        binding.offer.setTextColor(textColor)
        binding.offer.backgroundTintList = backgroundTint
    }


    private fun setSearch(check: Boolean) {
        val backgroundTint = check then { resources.getColorStateList(R.color.orange_60_new, null) } ?: resources.getColorStateList(R.color.black_new, null)
        val textColor = check then { ContextCompat.getColor(context, R.color.orange_new) } ?: ContextCompat.getColor(context, R.color.white_60_new)

        binding.search.setTextColor(textColor)
        binding.search.backgroundTintList = backgroundTint
    }

}

interface TripRadioOnClickListener {
    fun onClick(type: TripRad)
}


enum class TripRad {
    OFFER, SEARCH
}