package gr.fellow.fellow_traveller.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.domain.review.Review
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.usecase.review.GetUserReviewsUseCase
import gr.fellow.fellow_traveller.usecase.user.GetUserInfoByIdUseCase

class UserInfoDetailsViewModel
@ViewModelInject
constructor(
    private val getUserInfoByIdUseCase: GetUserInfoByIdUseCase,
    private val getUserReviewsUseCase: GetUserReviewsUseCase
) : BaseViewModel() {

    private var userId: String? = null

    private val _user = MutableLiveData<UserInfo>()
    val user: LiveData<UserInfo> = _user

    private val _reviews = MutableLiveData<MutableList<Review>>()
    val reviews: LiveData<MutableList<Review>> = _reviews


    fun loadUserInfo() {
        launch(true) {
            val responseUser = getUserInfoByIdUseCase(userId.toString())
            val response = getUserReviewsUseCase(userId.toString())
            _user.value = responseUser
            _reviews.value = response
        }
    }


    fun loadReviews() {
        launch(true) {
            val response = getUserReviewsUseCase(userId.toString())
            _reviews.value = response
        }
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

}