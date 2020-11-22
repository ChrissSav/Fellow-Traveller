package gr.fellow.fellow_traveller.usecase.review

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetUserReviewsUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(targetId: String) =
        dataSource.getUserReviews(targetId)
}

