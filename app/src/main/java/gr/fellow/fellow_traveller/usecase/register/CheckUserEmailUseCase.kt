package gr.fellow.fellow_traveller.usecase.register

import android.content.Context
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import retrofit2.Response

class CheckUserEmailUseCase(
    private val context: Context,
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(email: String): ResultWrapper<StatusHandleResponse> {
        return try {
            return when (val response = dataSource.checkUserEmail(email)) {
                is ResultWrapper.Success ->
                    response
                is ResultWrapper.Error ->
                    ResultWrapper.Error(ErrorResponse(msg = context.resources.getString(R.string.ERROR_EMAIL_ALREADY_EXISTS)))
            }
        } catch (e: BaseApiException) {
            ResultWrapper.Error(ErrorResponse(e.code, e.msg))
        }
    }
}
