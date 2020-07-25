package gr.fellow.fellow_traveller.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.usecase.CheckUserEmailUseCase
import gr.fellow.fellow_traveller.usecase.CheckUserPhoneUseCase
import gr.fellow.fellow_traveller.usecase.RegisterUserUseCase
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class UseCasesModule {


    @Singleton
    @Provides
    fun provideCheckPhoneUseCase(dataSource: FellowDataSource): CheckUserPhoneUseCase {
        return CheckUserPhoneUseCase(dataSource)
    }


    @Singleton
    @Provides
    fun provideCheckEmailUseCase(dataSource: FellowDataSource): CheckUserEmailUseCase {
        return CheckUserEmailUseCase(dataSource)
    }

    @Singleton
    @Provides
    fun provideRegisterUserUseCase(dataSource: FellowDataSource): RegisterUserUseCase {
        return RegisterUserUseCase(dataSource)
    }
}