package gr.fellow.fellow_traveller.usecase.register

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource

class CheckUserEmailUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(email: String): ResultWrapper<String> {
        return dataSource.checkUserEmail(email)
    }
}
