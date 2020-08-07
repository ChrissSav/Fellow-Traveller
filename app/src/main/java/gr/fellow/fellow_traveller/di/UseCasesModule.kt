package gr.fellow.fellow_traveller.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.usecase.LoadUserInfoUseCase
import gr.fellow.fellow_traveller.usecase.LoginUseCase
import gr.fellow.fellow_traveller.usecase.LogoutUseCase
import gr.fellow.fellow_traveller.usecase.home.*
import gr.fellow.fellow_traveller.usecase.newtrip.GetPlaceFromPlacesUseCase
import gr.fellow.fellow_traveller.usecase.register.*
import gr.fellow.fellow_traveller.usecase.trips.RegisterTripRemoteUseCase


@InstallIn(ActivityComponent::class)
@Module
class UseCasesModule {

    @ActivityScoped
    @Provides
    fun provideCheckPhoneUseCase(dataSource: FellowDataSource, context: Context): CheckUserPhoneUseCase {
        return CheckUserPhoneUseCase(context, dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideCheckEmailUseCase(dataSource: FellowDataSource, context: Context): CheckUserEmailUseCase {
        return CheckUserEmailUseCase(context, dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideRegisterUserUseCase(dataSource: FellowDataSource, context: Context): RegisterUserUseCase {
        return RegisterUserUseCase(context, dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideLoginUseCase(dataSource: FellowDataSource, context: Context): LoginUseCase {
        return LoginUseCase(context, dataSource)
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
    fun provideCheckLoginUseCase(sharedPreferences: SharedPreferences): CheckIfUserIsLoginUseCase {
        return CheckIfUserIsLoginUseCase(sharedPreferences)
    }

    @ActivityScoped
    @Provides
    fun provideLoadUserInfoUseCase(dataSource: FellowDataSource): LoadUserInfoUseCase {
        return LoadUserInfoUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideLoadLogoutUseCase(dataSource: FellowDataSource, sharedPreferences: SharedPreferences): LogoutUseCase {
        return LogoutUseCase(dataSource, sharedPreferences)
    }


    @ActivityScoped
    @Provides
    fun provideGetUserCarsUseCase(dataSource: FellowDataSource): GetUserCarsLocalUseCase {
        return GetUserCarsLocalUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideGetUserCarsRemoteUseCase(dataSource: FellowDataSource): GetUserCarsRemoteUseCase {
        return GetUserCarsRemoteUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideRegisterCarLocalUseCase(dataSource: FellowDataSource): RegisterCarLocalUseCase {
        return RegisterCarLocalUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideAddCarToRemoteUseCase(dataSource: FellowDataSource, context: Context): AddCarToRemoteUseCase {
        return AddCarToRemoteUseCase(dataSource, context)
    }


    @ActivityScoped
    @Provides
    fun provideDeleteCarUseCase(dataSource: FellowDataSource): DeleteCarUseCase {
        return DeleteCarUseCase(dataSource)
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
}