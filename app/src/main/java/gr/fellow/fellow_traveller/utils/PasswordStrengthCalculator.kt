package gr.fellow.fellow_traveller.utils

import android.text.Editable
import androidx.lifecycle.MutableLiveData

class PasswordStrengthCalculator {


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
        }
    }

}