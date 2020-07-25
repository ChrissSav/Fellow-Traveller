package gr.fellow.fellow_traveller.usecase

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import retrofit2.Response

class CheckUserEmailUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(email: String): ResultWrapper<StatusHandleResponse> {
        return dataSource.checkUserEmail(email)
    }
}
