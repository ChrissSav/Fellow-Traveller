package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.utils.roomCall

class LogoutUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(): Boolean {
        return roomCall {
            dataSource.logoutRemote()
            dataSource.deleteAllCars()
            dataSource.logoutUserLocal()
            true
        }
    }

}