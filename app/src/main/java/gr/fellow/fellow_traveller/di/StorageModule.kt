package gr.fellow.fellow_traveller.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import gr.fellow.fellow_traveller.ConnectivityHelper
import gr.fellow.fellow_traveller.data.FellowDataSourceImpl
import gr.fellow.fellow_traveller.data.FellowRepository
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.FellowRepositoryImpl
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object StorageModule {

    @Singleton
    @Provides
    fun provideRepository(
        service: FellowService, connectivityHelper: ConnectivityHelper, context: Context
        ,sharedPreferences: SharedPreferences
    ): FellowRepository {
        return FellowRepositoryImpl(context, service, connectivityHelper, sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideDataSource(repository: FellowRepository): FellowDataSource {
        return FellowDataSourceImpl(repository)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("FELLOW_TRAVELLER", Context.MODE_PRIVATE)


}