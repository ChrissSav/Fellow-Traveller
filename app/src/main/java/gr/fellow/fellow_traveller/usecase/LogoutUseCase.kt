package gr.fellow.fellow_traveller.usecase

import android.content.SharedPreferences
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.roomCall
import gr.fellow.fellow_traveller.set
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN

class LogoutUseCase(
    private val dataSource: FellowDataSource,
    private val sharedPreferences: SharedPreferences
) {
    suspend operator fun invoke() {
        roomCall {
            sharedPreferences[PREFS_AUTH_TOKEN] = null
            dataSource.logoutUser()
        }
    }

}