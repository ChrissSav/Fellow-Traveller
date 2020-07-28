package gr.fellow.fellow_traveller.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.usecase.*
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class UseCasesModule {


    @Singleton
    @Provides
    fun provideCheckPhoneUseCase(dataSource: FellowDataSource, context: Context): CheckUserPhoneUseCase {
        return CheckUserPhoneUseCase(context,dataSource)
    }


    @Singleton
    @Provides
    fun provideCheckEmailUseCase(dataSource: FellowDataSource,context: Context): CheckUserEmailUseCase {
        return CheckUserEmailUseCase(context, dataSource)
    }

    @Singleton
    @Provides
    fun provideRegisterUserUseCase(dataSource: FellowDataSource,context: Context): RegisterUserUseCase {
        return RegisterUserUseCase(context, dataSource)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(dataSource: FellowDataSource,context: Context): LoginUseCase {
        return LoginUseCase(context, dataSource)
    }


    @Singleton
    @Provides
    fun provideRegisterUserLocal(dataSource: FellowDataSource): RegisterUserLocalUseCase {
        return RegisterUserLocalUseCase(dataSource)
    }

    @Singleton
    @Provides
    fun provideCheckLoginUseCase(sharedPreferences: SharedPreferences): CheckIfUserIsLoginUseCase {
        return CheckIfUserIsLoginUseCase(sharedPreferences)
    }
}