package gr.fellow.fellow_traveller.domain.review

import gr.fellow.fellow_traveller.domain.user.UserBase

data class Review(
    val user: UserBase,
    val rate: Int,
    val timestamp: Long
)