package com.badcompany.pitak.di.main

import com.badcompany.data.FileUploadRepositoryImpl
import com.badcompany.data.mapper.PhotoMapper
import com.badcompany.data.repository.FileUploadRemote
import com.badcompany.data.source.FileUploadDataStoreFactory
import com.badcompany.data.source.FileUploadRemoteDataStore
import com.badcompany.domain.repository.FileUploadRepository
import com.badcompany.domain.usecases.UploadPhoto
import com.badcompany.remote.ApiService
import com.badcompany.remote.FileUploadRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object MainModule {

//    @Singleton
//    @Provides
//    @JvmStatic
//    fun provideRequestOptions(): RequestOptions {
//        return RequestOptions
//            .placeholderOf(R.drawable.white_background)
//            .error(R.drawable.white_background)
//    }



}