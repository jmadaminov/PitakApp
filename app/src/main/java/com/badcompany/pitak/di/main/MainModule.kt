package com.badcompany.pitak.di.main

import com.badcompany.data.CarRepositoryImpl
import com.badcompany.data.mapper.CarColorMapper
import com.badcompany.data.mapper.CarMapper
import com.badcompany.data.mapper.CarModelMapper
import com.badcompany.data.repository.CarRemote
import com.badcompany.data.source.CarDataStoreFactory
import com.badcompany.data.source.CarRemoteDataStore
import com.badcompany.domain.repository.CarRepository
import com.badcompany.domain.usecases.*
import com.badcompany.remote.ApiService
import com.badcompany.remote.CarRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object MainModule {

//    @Singleton
//    @Provides @MainScope
//    @JvmStatic
//    fun provideRequestOptions(): RequestOptions {
//        return RequestOptions
//            .placeholderOf(R.drawable.white_background)
//            .error(R.drawable.white_background)
//    }



}