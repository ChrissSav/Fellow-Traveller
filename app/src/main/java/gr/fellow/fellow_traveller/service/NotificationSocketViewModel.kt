package gr.fellow.fellow_traveller.service

import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationSocketViewModel
@Inject
constructor() : BaseViewModel() {

    val notificationCount = MutableLiveData<Int>()

    fun updateNotificationCount(num: Int) {
        launch {
            notificationCount.value = num
        }
    }
}