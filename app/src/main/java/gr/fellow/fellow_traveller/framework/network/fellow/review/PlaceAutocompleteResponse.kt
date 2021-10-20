package gr.fellow.fellow_traveller.framework.network.fellow.review


import gr.fellow.fellow_traveller.framework.network.fellow.user.UserBaseResponse

data class ReviewResponse(
    val user: UserBaseResponse,
    val rate: Float,
    val timestamp: Long,
)

data class RegisterReviewRequest(
    val targetId: String,
    val rate: Float,
)