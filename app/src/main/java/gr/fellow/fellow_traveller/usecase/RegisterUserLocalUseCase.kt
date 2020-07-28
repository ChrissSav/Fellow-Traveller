package gr.fellow.fellow_traveller.usecase

import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.mappers.toRegisteredUserEntity
import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.room.FellowDatabase
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity


class RegisterUserLocalUseCase(
    private val dataSource: FellowDataSource
){

    suspend operator fun invoke(userLoginResponse: UserLoginResponse) {
        val user = userLoginResponse.toRegisteredUserEntity()
        dataSource.registerUserAuth(user)
    }

}