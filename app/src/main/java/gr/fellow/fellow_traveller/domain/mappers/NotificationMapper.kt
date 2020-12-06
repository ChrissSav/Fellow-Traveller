package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.domain.notification.NotificationTrip
import gr.fellow.fellow_traveller.framework.network.fellow.notification.NotificationResponse
import gr.fellow.fellow_traveller.framework.network.fellow.notification.NotificationTripResponse


fun NotificationResponse.mapToNotification() =
    Notification(id, user.mapToUserBase(), type, read, trip.mapToNotificationTrip(), timestamp)


fun NotificationTripResponse.mapToNotificationTrip() =
    NotificationTrip(id, destinationFrom, destinationTo, status)