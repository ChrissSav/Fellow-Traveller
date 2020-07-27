package gr.fellow.fellow_traveller.usecase

import android.content.Context
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse

class RegisterUserUseCase(
    private val context: Context,
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phone: String
    ): ResultWrapper<UserInfoResponse> {
        return try {
            val res = dataSource.registerUser(firstName, lastName, email, password, phone)
            if (res is ResultWrapper.Error){
                when (res.error.code) {
                    200 -> res.error.msg =
                        context.resources.getString(R.string.ERROR_PHONE_ALREADY_EXISTS)
                    201 -> res.error.msg =
                        context.resources.getString(R.string.ERROR_EMAIL_ALREADY_EXISTS)
                    else -> res.error.msg =
                        context.resources.getString(R.string.ERROR_API_UNREACHABLE)
                }
            }
            res
        } catch (e: BaseApiException) {
            ResultWrapper.Error(ErrorResponse(e.code, e.msg))
        }
    }

}