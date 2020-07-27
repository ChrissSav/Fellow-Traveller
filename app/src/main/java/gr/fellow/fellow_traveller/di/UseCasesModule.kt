package gr.fellow.fellow_traveller.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.usecase.CheckUserEmailUseCase
import gr.fellow.fellow_traveller.usecase.CheckUserPhoneUseCase
import gr.fellow.fellow_traveller.usecase.LoginUseCase
import gr.fellow.fellow_traveller.usecase.RegisterUserUseCase
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
    fun provideLoginUseCase(dataSource: FellowDataSource,context: Context, userAuthDao: UserAuthDao): LoginUseCase {
        return LoginUseCase(context, dataSource,userAuthDao)
    }
}