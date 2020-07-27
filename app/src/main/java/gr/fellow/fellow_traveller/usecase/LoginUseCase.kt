package gr.fellow.fellow_traveller.usecase

import android.content.Context
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.dao.UserDao
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

class LoginUseCase(
    private val context: Context,
    private val dataSource: FellowDataSource,
    private val userAuthDao: UserAuthDao
) {

    suspend operator fun invoke(
        username: String,
        password: String
    ): ResultWrapper<UserLoginResponse> {
        return try {
            return when (val response = dataSource.loginUser(username, password)) {
                is ResultWrapper.Success -> {
                    val user =  RegisteredUserEntity( response.data.id, response.data.firstName,response.data.lastName,
                        response.data.rate,response.data.reviews,response.data.picture,response.data.aboutMe,response.data.phone,response.data.emailAddress)
                    userAuthDao.insertUser(user)
                    response
                }
                is ResultWrapper.Error ->
                    ResultWrapper.Error(ErrorResponse(msg = context.resources.getString(R.string.ERROR_INVALID_CREDENTIALS)))
            }
        } catch (e: BaseApiException) {
            ResultWrapper.Error(ErrorResponse(e.code, e.msg))
        }
    }

}