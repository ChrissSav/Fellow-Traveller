package gr.fellow.fellow_traveller.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Fellow

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleService
