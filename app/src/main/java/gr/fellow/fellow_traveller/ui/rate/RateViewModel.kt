package gr.fellow.fellow_traveller.ui.rate

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.internalError
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.usecase.notification.UpdateNotificationsUseCase
import gr.fellow.fellow_traveller.usecase.review.CheckReviewUseCase
import gr.fellow.fellow_traveller.usecase.review.RegisterReviewUseCase


class RateViewModel
@ViewModelInject
constructor(
    private val checkReviewUseCase: CheckReviewUseCase,
    private val registerReviewUseCase: RegisterReviewUseCase,
    private val updateNotificationsUseCase: UpdateNotificationsUseCase
) : BaseViewModel() {

    private val _success = SingleLiveEvent<Boolean>()
    val success: LiveData<Boolean> = _success

    fun registerRate(userId: String, rate: Float) {
        launch(true) {
            registerReviewUseCase(userId, rate)
            _success.value = true

        }
    }

    fun checkReview(notification: Notification) {
        launch(true) {
            if (!notification.isRead) {
                updateNotificationsUseCase(notification.id)
            }
            val response = checkReviewUseCase(notification.user.id)
            if (!response) {
                error.value = internalError(R.string.ERROR_RATING_CANT_REGISTER_THE_RATE)
            }
        }
    }
}
