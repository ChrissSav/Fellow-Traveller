package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.review.Review
import gr.fellow.fellow_traveller.framework.network.fellow.review.ReviewResponse

fun ReviewResponse.mapReview() = Review(
    user.mapToUserBase(), rate, timestamp
)