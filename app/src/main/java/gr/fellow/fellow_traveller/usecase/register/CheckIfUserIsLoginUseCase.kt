package gr.fellow.fellow_traveller.usecase.register

import android.content.SharedPreferences
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN

class CheckIfUserIsLoginUseCase(
    private val sharedPreferences: SharedPreferences
) {

    operator fun invoke(): Boolean {
        return !sharedPreferences.getString(PREFS_AUTH_TOKEN, "").isNullOrBlank()
    }
}
