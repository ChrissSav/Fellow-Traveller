package gr.fellow.fellow_traveller.ui.rate

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.domain.internalError
import gr.fellow.fellow_traveller.usecase.review.CheckReviewUseCase
import gr.fellow.fellow_traveller.usecase.review.RegisterReviewUseCase


class RateViewModel
@ViewModelInject
constructor(
    private val checkReviewUseCase: CheckReviewUseCase,
    private val registerReviewUseCase: RegisterReviewUseCase
) : BaseViewModel() {

    private val _success = SingleLiveEvent<Boolean>()
    val success: LiveData<Boolean> = _success

    fun registerRate(userId: String, rate: Float) {
        launch(true) {
            when (val response = registerReviewUseCase(userId, rate)) {
                is ResultWrapper.Success -> {
                    _success.value = true
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    fun checkReview(userId: String) {
        launch(true) {
            when (val response = checkReviewUseCase(userId)) {
                is ResultWrapper.Success -> {
                    if (!response.data) {
                        error.value = internalError(R.string.ERROR_REVIEW_CANT_REGISTER_THE_REVIEW)
                    }
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }
}