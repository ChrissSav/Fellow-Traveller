package gr.fellow.fellow_traveller.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.usecase.LoadUserInfoUseCase
import gr.fellow.fellow_traveller.usecase.auth.LoginUseCase
import gr.fellow.fellow_traveller.usecase.auth.LogoutUseCase
import gr.fellow.fellow_traveller.usecase.auth.VerifyAccountUseCase
import gr.fellow.fellow_traveller.usecase.home.*
import gr.fellow.fellow_traveller.usecase.newtrip.GetGeometryFormPlaceUseCase
import gr.fellow.fellow_traveller.usecase.newtrip.GetPlaceFromPlacesUseCase
import gr.fellow.fellow_traveller.usecase.newtrip.RegisterTripRemoteUseCase
import gr.fellow.fellow_traveller.usecase.register.CheckUserEmailUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserUseCase
import gr.fellow.fellow_traveller.usecase.trips.BookTripUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsCreatorRemoteUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsPassengerRemoteUseCase
import gr.fellow.fellow_traveller.usecase.trips.SearchTripsUseCase


@InstallIn(ActivityComponent::class)
@Module
class UseCasesModule {


    @ActivityScoped
    @Provides
    fun provideVerifyAccountUseCase(dataSource: FellowDataSource): VerifyAccountUseCase {
        return VerifyAccountUseCase(dataSource)
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
    fun provideLoadUserInfoUseCase(dataSource: FellowDataSource): LoadUserInfoUseCase {
        return LoadUserInfoUseCase(dataSource)
    }

    @ActivityScoped
    @Provides
    fun provideLoadLogoutUseCase(dataSource: FellowDataSource): LogoutUseCase {
        return LogoutUseCase(dataSource)
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
    fun provideAddCarToRemoteUseCase(dataSource: FellowDataSource): AddCarToRemoteUseCase {
        return AddCarToRemoteUseCase(dataSource)
    }


    @ActivityScoped
    @Provides
    fun provideDeleteCarUseCase(dataSource: FellowDataSource): DeleteUserLocalCars {
        return DeleteUserLocalCars(dataSource)
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