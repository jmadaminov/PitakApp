package com.badcompany.pitak.di

import com.badcompany.data.FileUploadRepositoryImpl
import com.badcompany.data.mapper.PhotoMapper
import com.badcompany.data.repository.FileUploadRemote
import com.badcompany.data.source.FileUploadDataStoreFactory
import com.badcompany.data.source.FileUploadRemoteDataStore
import com.badcompany.domain.repository.FileUploadRepository
import com.badcompany.domain.usecases.UploadPhoto
import com.badcompany.pitak.BuildConfig
import com.badcompany.pitak.di.main.MainScope
import com.badcompany.remote.ApiService
import com.badcompany.remote.ApiServiceFactory
import com.badcompany.remote.FileUploadRemoteImpl
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {


    @Provides
    @JvmStatic
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }


    @Provides
    @JvmStatic
    fun provideUploadCarPhotoUseCase(fileUploadRepository: FileUploadRepository): UploadPhoto {
        return UploadPhoto(fileUploadRepository)
    }

    @Provides
    @JvmStatic
    fun provideFileUploadRepository(fileUploadDataStoreFactory: FileUploadDataStoreFactory,
                                    photoMapper: PhotoMapper): FileUploadRepository {
        return FileUploadRepositoryImpl(fileUploadDataStoreFactory, photoMapper)
    }


    @Provides
    @JvmStatic
    fun providePhotoMapper(): PhotoMapper {
        return PhotoMapper()
    }

    @Provides
    @JvmStatic
    fun provideFileUploadDataStoreFactory(fileUploadRemoteDataStore: FileUploadRemoteDataStore): FileUploadDataStoreFactory {
        return FileUploadDataStoreFactory(fileUploadRemoteDataStore)
    }

    @Provides
    @JvmStatic
    fun provideFileUploadRemoteDataStore(fileUploadRemote: FileUploadRemote): FileUploadRemoteDataStore {
        return FileUploadRemoteDataStore(fileUploadRemote)
    }

    @Provides
    @JvmStatic
    fun provideFileUploadRemote(apiService: ApiService,
                                photoMapper: com.badcompany.remote.mapper.PhotoMapper): FileUploadRemote {
        return FileUploadRemoteImpl(apiService, photoMapper)
    }

    @Provides
    @JvmStatic
    fun provideRemoteFileUploadPhotoMapper(): com.badcompany.remote.mapper.PhotoMapper {
        return com.badcompany.remote.mapper.PhotoMapper()
    }


//    @Singleton
//    @Provides
//    @JvmStatic
//    fun provideRequestOptions(): RequestOptions {
//        return RequestOptions
//            .placeholderOf(R.drawable.white_background)
//            .error(R.drawable.white_background)
//    }

//    @JvmStatic
//    @Singleton
//    @Provides
//    fun provideGlideInstance(application: Application?,
//                             requestOptions: RequestOptions?): RequestManager {
//        return Glide.with(application!!)
//            .setDefaultRequestOptions(requestOptions!!)
//    }

//    @JvmStatic
//    @Singleton
//    @Provides
//    fun provideAppDrawable(application: Application?): Drawable {
//        return ContextCompat.getDrawable(application!!, R.drawable.logo)
//    }

}