package gr.fellow.fellow_traveller.usecase

import android.content.Context
import android.content.SharedPreferences
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.get
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN

class CheckIfUserIsLoginUseCase(
    private val sharedPreferences: SharedPreferences
) {

    operator fun invoke(): Boolean {
        return !sharedPreferences.getString(PREFS_AUTH_TOKEN, "").isNullOrBlank()
    }
}
