package gr.fellow.fellow_traveller.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gr.fellow.fellow_traveller.data.FellowDataSourceImpl
import gr.fellow.fellow_traveller.data.FellowRepository
import gr.fellow.fellow_traveller.data.GoogleServiceRepository
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.FellowRepositoryImpl
import gr.fellow.fellow_traveller.framework.GoogleServiceRepositoryImpl
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import gr.fellow.fellow_traveller.framework.network.google.PlaceApiService
import gr.fellow.fellow_traveller.room.FellowDatabase
import gr.fellow.fellow_traveller.room.dao.CarDao
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.utils.ConnectivityHelper
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object StorageModule {

    @Singleton
    @Provides
    fun provideRepository(
        service: FellowService, connectivityHelper: ConnectivityHelper, sharedPreferences: SharedPreferences, userAuthDao: UserAuthDao, carDao: CarDao
    ): FellowRepository {
        return FellowRepositoryImpl(service, connectivityHelper, sharedPreferences, userAuthDao, carDao)
    }


    @Singleton
    @Provides
    fun provideGoogleServiceRepository(connectivityHelper: ConnectivityHelper, service: PlaceApiService): GoogleServiceRepository {
        return GoogleServiceRepositoryImpl(connectivityHelper, service)
    }


    @Singleton
    @Provides
    fun provideDataSource(
        repository: FellowRepository, googleServiceRepository: GoogleServiceRepository
    ): FellowDataSource {
        return FellowDataSourceImpl(repository, googleServiceRepository)
    }


    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("FELLOW_TRAVELLER", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideDatabase(application: Application): FellowDatabase =
        Room.databaseBuilder(application.applicationContext, FellowDatabase::class.java, "Fellow_traveller-db").build()


    @Singleton
    @Provides
    fun providesProductDao(fellowDatabase: FellowDatabase): UserAuthDao {
        return fellowDatabase.userAuthDao()
    }

    @Singleton
    @Provides
    fun providesCarDao(fellowDatabase: FellowDatabase): CarDao {
        return fellowDatabase.carDao()
    }

}