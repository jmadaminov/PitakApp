package com.badcompany.pitak.hilt

import com.badcompany.pitak.BuildConfig
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.remote.ApiServiceFactory
import com.badcompany.remote.AuthorizedApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @ExperimentalSplittiesApi
    @Binds
    fun provideAuthorizedApiService(): AuthorizedApiService {
        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG,
                                                          AppPreferences.token,
                                                          AppPreferences.language)
    }


//    @Singleton
//    @Provides
//    fun provideAuthorizedApiService(): AuthorizedApiService {
//        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG)
//    }


}


