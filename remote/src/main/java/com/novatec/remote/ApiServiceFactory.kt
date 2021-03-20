package com.novatec.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.novatec.core.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Provide "make" methods to create instances of [ApiService]
 * and its related dependencies, such as OkHttpClient, Gson, etc.
 */
object ApiServiceFactory {

    fun makeApiService(isDebug: Boolean, lang: String): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(makeOkHttpClient(makeLoggingInterceptor(isDebug), lang))
            .addConverterFactory(GsonConverterFactory.create(makeGson()))
            .build().create(ApiService::class.java)
    }


    fun makeAuthorizedApiService(isDebug: Boolean,
                                 token: String,
                                 lang: String): AuthorizedApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor(isDebug))
            .addInterceptor(AuthInterceptor(token))
            .addInterceptor(LangInterceptor(lang))
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(makeGson()))
            .build().create(AuthorizedApiService::class.java)
    }


    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                                 lang: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(LangInterceptor(lang))
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }

}