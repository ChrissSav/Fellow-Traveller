package gr.fellow.fellow_traveller.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.fellow.fellow_traveller.data.FellowDataSourceImpl
import gr.fellow.fellow_traveller.data.FellowRepository
import gr.fellow.fellow_traveller.data.FirebaseRepository
import gr.fellow.fellow_traveller.data.GoogleServiceRepository
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.FellowRepositoryImpl
import gr.fellow.fellow_traveller.framework.FirebaseRepositoryImpl
import gr.fellow.fellow_traveller.framework.GoogleServiceRepositoryImpl
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import gr.fellow.fellow_traveller.framework.network.google.PlaceApiService
import gr.fellow.fellow_traveller.room.FellowDatabase
import gr.fellow.fellow_traveller.room.MIGRATION_1_2
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.usecase.notification.GetNotificationsSocketUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StorageModule {

    @Singleton
    @Provides
    fun provideRepository(service: FellowService, sharedPreferences: SharedPreferences, userAuthDao: UserAuthDao): FellowRepository =
        FellowRepositoryImpl(service, sharedPreferences, userAuthDao)


    @Singleton
    @Provides
    fun provideFirebaseRepository(firebaseStorage: FirebaseStorage, firebaseDatabase: FirebaseDatabase): FirebaseRepository =
        FirebaseRepositoryImpl(firebaseStorage, firebaseDatabase)


    @Singleton
    @Provides
    fun provideGetNotificationsSocketUseCase(dataSource: FellowDataSource): GetNotificationsSocketUseCase {
        return GetNotificationsSocketUseCase(dataSource)
    }

    @Singleton
    @Provides
    fun provideGoogleServiceRepository(service: PlaceApiService): GoogleServiceRepository =
        GoogleServiceRepositoryImpl(service)


    @Singleton
    @Provides
    fun provideDataSource(repository: FellowRepository, googleServiceRepository: GoogleServiceRepository, firebaseRepository: FirebaseRepository): FellowDataSource =
        FellowDataSourceImpl(repository, googleServiceRepository, firebaseRepository)


    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("FELLOW_TRAVELLER", Context.MODE_PRIVATE)


    @Singleton
    @Provides
    fun provideDatabase(application: Application): FellowDatabase =
        Room.databaseBuilder(application.applicationContext, FellowDatabase::class.java, "Fellow_traveller-db")
            .addMigrations(MIGRATION_1_2)
            .build()


    @Singleton
    @Provides
    fun providesProductDao(fellowDatabase: FellowDatabase): UserAuthDao =
        fellowDatabase.userAuthDao()


}