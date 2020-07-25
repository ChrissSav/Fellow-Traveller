package gr.fellow.fellow_traveller.usecase

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse

class CheckUserPhoneUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(phone: String): ResultWrapper<StatusHandleResponse> {
        return dataSource.checkUserPhone(phone)
    }

}