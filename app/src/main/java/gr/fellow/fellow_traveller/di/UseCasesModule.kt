package gr.fellow.fellow_traveller.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.usecase.auth.*
import gr.fellow.fellow_traveller.usecase.firabase.SendMessageFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.firabase.UploadPictureFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.home.*
import gr.fellow.fellow_traveller.usecase.newtrip.GetGeometryFormPlaceUseCase
import gr.fellow.fellow_traveller.usecase.newtrip.GetPlaceFromPlacesUseCase
import gr.fellow.fellow_traveller.usecase.newtrip.RegisterTripRemoteUseCase
import gr.fellow.fellow_traveller.usecase.notification.GetNotificationsUseCase
import gr.fellow.fellow_traveller.usecase.notification.UpdateNotificationsUseCase
import gr.fellow.fellow_traveller.usecase.register.CheckUserEmailUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserUseCase
import gr.fellow.fellow_traveller.usecase.review.CheckReviewUseCase
import gr.fellow.fellow_traveller.usecase.review.GetUserReviewsUseCase
import gr.fellow.fellow_traveller.usecase.review.RegisterReviewUseCase
import gr.fellow.fellow_traveller.usecase.trips.*
import gr.fellow.fellow_traveller.usecase.user.GetUserInfoByIdUseCase
import gr.fellow.fellow_traveller.usecase.user.LoadUserLocalInfoUseCase


@InstallIn(ActivityComponent::class)
@Module
class UseCasesModule {

    @ActivityScoped
    @Provides
    fun provideSendFirebaseMessageUseCase(dataSource: FellowDataSource): SendMessageFirebaseUseCase {
        return SendMessageFirebaseUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideGetUserReviewsUseCase(dataSource: FellowDataSource): GetUserReviewsUseCase {
        return GetUserReviewsUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideUpdateUserPictureFirebaseUseCase(dataSource: FellowDataSource): UploadPictureFirebaseUseCase {
        return UploadPictureFirebaseUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideUpdateUserMessengerUseCase(dataSource: FellowDataSource): UpdateUserMessengerUseCase {
        return UpdateUserMessengerUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideGetTripByIdUseCase(dataSource: FellowDataSource): GetTripInvolvedByIdUseCase {
        return GetTripInvolvedByIdUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideCheckReviewUseCase(dataSource: FellowDataSource): CheckReviewUseCase {
        return CheckReviewUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideRegisterReviewUseCase(dataSource: FellowDataSource): RegisterReviewUseCase {
        return RegisterReviewUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideGetNotificationsUseCase(dataSource: FellowDataSource): GetNotificationsUseCase {
        return GetNotificationsUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideUpdateNotificationsUseCase(dataSource: FellowDataSource): UpdateNotificationsUseCase {
        return UpdateNotificationsUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideChangePasswordUseCase(dataSource: FellowDataSource): ChangePasswordUseCase {
        return ChangePasswordUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideExitFromTripUseCase(dataSource: FellowDataSource): ExitFromTripUseCase {
        return ExitFromTripUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideDeleteTripUseCase(dataSource: FellowDataSource): DeleteTripUseCase {
        return DeleteTripUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideGetUserInfoRemoteUseCase(dataSource: FellowDataSource): GetUserInfoRemoteUseCase {
        return GetUserInfoRemoteUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideGetUserInfoByIdUseCase(dataSource: FellowDataSource): GetUserInfoByIdUseCase {
        return GetUserInfoByIdUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideUpdateUserPictureUseCase(dataSource: FellowDataSource): UpdateUserPictureUseCase {
        return UpdateUserPictureUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideUpdateAccountInfoUseCase(dataSource: FellowDataSource): UpdateAccountInfoUseCase {
        return UpdateAccountInfoUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideLogoutRemoteUseCase(dataSource: FellowDataSource): LogoutRemoteUseCase {
        return LogoutRemoteUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideVerifyAccountUseCase(dataSource: FellowDataSource): VerifyAccountUseCase {
        return VerifyAccountUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideResetPasswordUserCase(dataSource: FellowDataSource): ResetPasswordUserCase {
        return ResetPasswordUserCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideForgotPasswordUserCase(dataSource: FellowDataSource): ForgotPasswordUserCase {
        return ForgotPasswordUserCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideVDeleteUserCars(dataSource: FellowDataSource): DeleteCarUseCase {
        return DeleteCarUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideCheckEmailUseCase(dataSource: FellowDataSource): CheckUserEmailUseCase {
        return CheckUserEmailUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideRegisterUserUseCase(dataSource: FellowDataSource): RegisterUserUseCase {
        return RegisterUserUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideLoginUseCase(dataSource: FellowDataSource): LoginUseCase {
        return LoginUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideGetPlaceFromPlacesUseCase(dataSource: FellowDataSource): GetPlaceFromPlacesUseCase {
        return GetPlaceFromPlacesUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideRegisterUserLocal(dataSource: FellowDataSource): RegisterUserLocalUseCase {
        return RegisterUserLocalUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideLoadUserInfoUseCase(dataSource: FellowDataSource): LoadUserLocalInfoUseCase {
        return LoadUserLocalInfoUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideLoadLogoutUseCase(dataSource: FellowDataSource): DeleteUserAuthLocalUseCase {
        return DeleteUserAuthLocalUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideGetUserCarsRemoteUseCase(dataSource: FellowDataSource): GetUserCarsRemoteUseCase {
        return GetUserCarsRemoteUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideAddCarToRemoteUseCase(dataSource: FellowDataSource): AddCarToRemoteUseCase {
        return AddCarToRemoteUseCase(dataSource)
    }



    @ActivityScoped
    @Provides
    fun provideRegisterTripRemoteUseCase(dataSource: FellowDataSource): RegisterTripRemoteUseCase {
        return RegisterTripRemoteUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideGetTripsAsCreatorRemoteUseCase(dataSource: FellowDataSource): GetTripsAsCreatorRemoteUseCase {
        return GetTripsAsCreatorRemoteUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideGetTripsTakePartRemoteUseCase(dataSource: FellowDataSource): GetTripsAsPassengerRemoteUseCase {
        return GetTripsAsPassengerRemoteUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideGetGeometryFormPlaceUseCase(dataSource: FellowDataSource): GetGeometryFormPlaceUseCase {
        return GetGeometryFormPlaceUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideSearchTripsUseCase(dataSource: FellowDataSource): SearchTripsUseCase {
        return SearchTripsUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideBookTripUseCase(dataSource: FellowDataSource): BookTripUseCase {
        return BookTripUseCase(dataSource)
    }
}