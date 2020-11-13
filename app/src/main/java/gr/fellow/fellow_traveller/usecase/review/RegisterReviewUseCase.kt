package gr.fellow.fellow_traveller.usecase.review

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.review.RegisterReviewRequest

class RegisterReviewUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(targetId: String, rate: Float) =
        dataSource.registerReview(RegisterReviewRequest(targetId, rate))
}

