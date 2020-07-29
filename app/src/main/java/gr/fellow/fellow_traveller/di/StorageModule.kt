package gr.fellow.fellow_traveller.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gr.fellow.fellow_traveller.ConnectivityHelper
import gr.fellow.fellow_traveller.data.FellowDataSourceImpl
import gr.fellow.fellow_traveller.data.FellowRepository
import gr.fellow.fellow_traveller.data.LocalRepository
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.sigleton.UserInfoSingle
import gr.fellow.fellow_traveller.framework.FellowRepositoryImpl
import gr.fellow.fellow_traveller.framework.LocalRepositoryImpl
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import gr.fellow.fellow_traveller.framework.network.google.PlaceApiService
import gr.fellow.fellow_traveller.room.FellowDatabase
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object StorageModule {

    @Singleton
    @Provides
    fun provideRepository(
        service: FellowService,
        connectivityHelper: ConnectivityHelper,
        sharedPreferences: SharedPreferences,
        servicePlace: PlaceApiService
    ): FellowRepository {
        return FellowRepositoryImpl(service,servicePlace, connectivityHelper, sharedPreferences)
    }


    @Singleton
    @Provides
    fun provideLocalRepositoryImpl(userAuthDao: UserAuthDao): LocalRepository {
        return LocalRepositoryImpl(userAuthDao)
    }

    @Singleton
    @Provides
    fun provideDataSource(
        repository: FellowRepository,
        repositoryLocal: LocalRepository
    ): FellowDataSource {
        return FellowDataSourceImpl(repository, repositoryLocal)
    }


    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("FELLOW_TRAVELLER", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideDatabase(application: Application): FellowDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            FellowDatabase::class.java,
            "Fellow_traveller-db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesProductDao(fellowDatabase: FellowDatabase): UserAuthDao {
        return fellowDatabase.userAuthDao()
    }

    @Singleton
    @Provides
    fun providesUserInfoSingle(): UserInfoSingle {
        return UserInfoSingle()
    }



}