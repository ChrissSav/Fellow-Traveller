package gr.fellow.fellow_traveller.usecase.register

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse

class CheckUserEmailUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(email: String): ResultWrapper<StatusHandleResponse> {
        return dataSource.checkUserEmail(email)
    }
}
