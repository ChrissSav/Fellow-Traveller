package gr.fellow.fellow_traveller.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.usecase.*
import gr.fellow.fellow_traveller.usecase.home.*
import gr.fellow.fellow_traveller.usecase.newtrip.GetPlaceFromPlacesUseCase
import gr.fellow.fellow_traveller.usecase.register.*
import gr.fellow.fellow_traveller.usecase.trips.RegisterTripRemoteUseCase
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class UseCasesModule {


    @Singleton
    @Provides
    fun provideCheckPhoneUseCase(
        dataSource: FellowDataSource,
        context: Context
    ): CheckUserPhoneUseCase {
        return CheckUserPhoneUseCase(
            context,
            dataSource
        )
    }


    @Singleton
    @Provides
    fun provideCheckEmailUseCase(
        dataSource: FellowDataSource,
        context: Context
    ): CheckUserEmailUseCase {
        return CheckUserEmailUseCase(
            context,
            dataSource
        )
    }

    @Singleton
    @Provides
    fun provideRegisterUserUseCase(
        dataSource: FellowDataSource,
        context: Context
    ): RegisterUserUseCase {
        return RegisterUserUseCase(
            context,
            dataSource
        )
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(dataSource: FellowDataSource, context: Context): LoginUseCase {
        return LoginUseCase(context, dataSource)
    }

    @Singleton
    @Provides
    fun provideGetPlaceFromPlacesUseCase(dataSource: FellowDataSource): GetPlaceFromPlacesUseCase {
        return GetPlaceFromPlacesUseCase(dataSource)
    }

    @Singleton
    @Provides
    fun provideRegisterUserLocal(dataSource: FellowDataSource): RegisterUserLocalUseCase {
        return RegisterUserLocalUseCase(
            dataSource
        )
    }

    @Singleton
    @Provides
    fun provideCheckLoginUseCase(sharedPreferences: SharedPreferences): CheckIfUserIsLoginUseCase {
        return CheckIfUserIsLoginUseCase(
            sharedPreferences
        )
    }

    @Singleton
    @Provides
    fun provideLoadUserInfoUseCase(dataSource: FellowDataSource): LoadUserInfoUseCase {
        return LoadUserInfoUseCase(dataSource)
    }

    @Singleton
    @Provides
    fun provideLoadLogoutUseCase(
        dataSource: FellowDataSource,
        sharedPreferences: SharedPreferences
    ): LogoutUseCase {
        return LogoutUseCase(dataSource, sharedPreferences)
    }


    @Singleton
    @Provides
    fun provideGetUserCarsUseCase(dataSource: FellowDataSource): GetUserCarsLocalUseCase {
        return GetUserCarsLocalUseCase(dataSource)
    }


    @Singleton
    @Provides
    fun provideGetUserCarsRemoteUseCase(dataSource: FellowDataSource): GetUserCarsRemoteUseCase {
        return GetUserCarsRemoteUseCase(dataSource)
    }


    @Singleton
    @Provides
    fun provideRegisterCarLocalUseCase(dataSource: FellowDataSource): RegisterCarLocalUseCase {
        return RegisterCarLocalUseCase(dataSource)
    }


    @Singleton
    @Provides
    fun provideAddCarToRemoteUseCase(
        dataSource: FellowDataSource,
        context: Context
    ): AddCarToRemoteUseCase {
        return AddCarToRemoteUseCase(dataSource, context)
    }


    @Singleton
    @Provides
    fun provideDeleteCarUseCase(dataSource: FellowDataSource): DeleteCarUseCase {
        return DeleteCarUseCase(dataSource)
    }

    @Singleton
    @Provides
    fun provideRegisterTripRemoteUseCase(dataSource: FellowDataSource): RegisterTripRemoteUseCase {
        return RegisterTripRemoteUseCase(dataSource)
    }

}