package com.badcompany.pitak.hilt

import com.badcompany.pitak.BuildConfig
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.remote.ApiService
import com.badcompany.remote.ApiServiceFactory
import com.badcompany.remote.AuthorizedApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }

    @ExperimentalSplittiesApi
    @Singleton
    @Provides
    fun provideAuthorizedApiService(): AuthorizedApiService {
        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG, AppPreferences.token, AppPreferences.language)
    }


//    @Singleton
//    @Provides
//    fun provideAuthorizedApiService(): AuthorizedApiService {
//        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG)
//    }


}


