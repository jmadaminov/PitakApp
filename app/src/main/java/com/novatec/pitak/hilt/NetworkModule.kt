package com.novatec.pitak.hilt

import com.novatec.pitak.BuildConfig
import com.novatec.pitak.util.AppPrefs
//import com.novatec.pitak.util.AuthAPIServiceTEST
import com.novatec.remote.ApiService
import com.novatec.remote.ApiServiceFactory
import com.novatec.remote.AuthorizedApiService
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
    @Provides
    fun provideAuthorizedApiService(): AuthorizedApiService {
        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG,
                                                          AppPrefs.token,
                                                          AppPrefs.language)
    }



}


