package com.novatec.epitak.hilt

import com.novatec.epitak.BuildConfig
import com.novatec.epitak.util.AppPrefs
import com.novatec.remote.ApiService
import com.novatec.remote.ApiServiceFactory
import com.novatec.remote.AuthorizedApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG, AppPrefs.language)
    }


    @Provides
    fun provideAuthorizedApiService(): AuthorizedApiService {
        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG,
                                                          AppPrefs.token,
                                                          AppPrefs.language)
    }


}


