package gr.fellow.fellow_traveller.utils

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.R

class PasswordStrengthCalculator {

    var strengthColor: MutableLiveData<Int> = MutableLiveData()

    var lowerCase: MutableLiveData<Int> = MutableLiveData(0)
    var upperCase: MutableLiveData<Int> = MutableLiveData(0)
    var digit: MutableLiveData<Int> = MutableLiveData(0)
    var specialChar: MutableLiveData<Int> = MutableLiveData(0)


    fun onTextChanged(char: Editable?) {
        if (char != null) {
            lowerCase.value = if (char.hasLowerCase()) 1 else 0
            upperCase.value = if (char.hasUpperCase()) 1 else 0
            digit.value = if (char.hasDigit()) 1 else 0
            specialChar.value = if (char.hasSpecialChar()) 1 else 0
            calculateStrength(char)
        }
    }

    private fun calculateStrength(password: CharSequence) {
        if (password.length in 0..7) {
            strengthColor.value = R.color.weak
        } else if (password.length in 8..10) {
            if (lowerCase.value == 1 || upperCase.value == 1 || digit.value == 1 || specialChar.value == 1) {
                strengthColor.value = R.color.medium
            }
        } else if (password.length in 11..16) {
            if (lowerCase.value == 1 || upperCase.value == 1 || digit.value == 1 || specialChar.value == 1) {
                if (lowerCase.value == 1 && upperCase.value == 1) {
                    strengthColor.value = R.color.strong
                }
            }
        } else if (password.length > 16) {
            if (lowerCase.value == 1 && upperCase.value == 1 && digit.value == 1 && specialChar.value == 1) {
                strengthColor.value = R.color.bulletproof
            }
        }
    }


}