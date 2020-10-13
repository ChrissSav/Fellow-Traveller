package gr.fellow.fellow_traveller.usecase.auth

import android.content.SharedPreferences
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN
import gr.fellow.fellow_traveller.utils.roomCall
import gr.fellow.fellow_traveller.utils.set

class LogoutUseCase(
    private val dataSource: FellowDataSource,
    private val sharedPreferences: SharedPreferences
) {
    suspend operator fun invoke() {
        roomCall {
            sharedPreferences[PREFS_AUTH_TOKEN] = null
            dataSource.logoutUserLocal()
        }
    }

}