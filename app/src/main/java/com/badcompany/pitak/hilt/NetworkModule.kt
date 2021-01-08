package com.badcompany.pitak.hilt

import com.badcompany.core.Constants
import com.badcompany.pitak.BuildConfig
import com.badcompany.pitak.util.AppPrefs
//import com.badcompany.pitak.util.AuthAPIServiceTEST
import com.badcompany.remote.ApiService
import com.badcompany.remote.ApiServiceFactory
import com.badcompany.remote.AuthInterceptor
import com.badcompany.remote.AuthorizedApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import splitties.experimental.ExperimentalSplittiesApi
import java.util.concurrent.TimeUnit
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

//    @ExperimentalSplittiesApi
//    @Provides
//    fun provideAuthorizedApiServiceTest(): AuthAPIServiceTEST {
//
//        val logging = HttpLoggingInterceptor()
//        logging.level = if (BuildConfig.DEBUG)
//            HttpLoggingInterceptor.Level.BODY
//        else
//            HttpLoggingInterceptor.Level.NONE
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .addInterceptor(AuthInterceptor(AppPrefs.token, AppPrefs.language))
//            .connectTimeout(20, TimeUnit.SECONDS)
//            .writeTimeout(20, TimeUnit.SECONDS)
//            .readTimeout(20, TimeUnit.SECONDS)
//            .build()
//
//        return Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
//                                                                 .setLenient()
//                                                                 .create()))
//            .build().create(AuthAPIServiceTEST::class.java)
//    }


}


