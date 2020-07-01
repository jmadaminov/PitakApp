package com.badcompany.pitak.di.main

import com.badcompany.data.DriverPostRepositoryImpl
import com.badcompany.data.mapper.DriverPostMapper
import com.badcompany.data.mapper.PlaceMapper
import com.badcompany.data.repository.DriverPostRemote
import com.badcompany.data.repository.PlaceRemote
import com.badcompany.data.source.DriverPostDataStoreFactory
import com.badcompany.data.source.DriverPostRemoteDataStore
import com.badcompany.data.source.PlaceDataStoreFactory
import com.badcompany.data.source.PlaceRemoteDataStore
import com.badcompany.domain.repository.DriverPostRepository
import com.badcompany.domain.usecases.DeleteDriverPost
import com.badcompany.domain.usecases.FinishDriverPost
import com.badcompany.domain.usecases.GetActiveDriverPost
import com.badcompany.domain.usecases.GetHistoryDriverPost
import com.badcompany.remote.ApiService
import com.badcompany.remote.DriverPostRemoteImpl
import com.badcompany.remote.PlaceRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object MainModule {

    @MainScope
    @Provides
    @JvmStatic
    fun provideGetActiveDriverPost(driverPostRepository: DriverPostRepository): GetActiveDriverPost {
        return GetActiveDriverPost(driverPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideDeleteDriverPost(driverPostRepository: DriverPostRepository): DeleteDriverPost {
        return DeleteDriverPost(driverPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideFinishDriverPost(driverPostRepository: DriverPostRepository): FinishDriverPost {
        return FinishDriverPost(driverPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideGetHistoryDriverPost(driverPostRepository: DriverPostRepository): GetHistoryDriverPost {
        return GetHistoryDriverPost(driverPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideDriverPostRepository(factory: DriverPostDataStoreFactory,
                                    driverPostMapper: DriverPostMapper): DriverPostRepository {
        return DriverPostRepositoryImpl(factory, driverPostMapper)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePlaceMapper(): PlaceMapper {
        return PlaceMapper()
    }

    @Provides
    @MainScope
    @JvmStatic
    fun provideDriverPostMapper(): DriverPostMapper {
        return DriverPostMapper()
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePlaceDataStoreFactory(placeRemoteDataStore: PlaceRemoteDataStore): PlaceDataStoreFactory {
        return PlaceDataStoreFactory(placeRemoteDataStore)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun provideDriverPostDataStoreFactory(driverPostRemoteDataStore: DriverPostRemoteDataStore): DriverPostDataStoreFactory {
        return DriverPostDataStoreFactory(driverPostRemoteDataStore)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePlaceRemoteDataStore(placeRemote: PlaceRemote): PlaceRemoteDataStore {
        return PlaceRemoteDataStore(placeRemote)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun provideDriverPostRemoteDataStore(driverDriverPostRemote: DriverPostRemote): DriverPostRemoteDataStore {
        return DriverPostRemoteDataStore(driverDriverPostRemote)
    }


    @Provides
    @MainScope
    @JvmStatic
    fun providePlaceRemote(apiService: ApiService,
                           placeMapper: com.badcompany.remote.mapper.PlaceMapper): PlaceRemote {
        return PlaceRemoteImpl(apiService, placeMapper)
    }


    @Provides
    @MainScope
    @JvmStatic
    fun provideDriverPostRemote(apiService: ApiService,
                                driverPostMapper: com.badcompany.remote.mapper.DriverPostMapper): DriverPostRemote {
        return DriverPostRemoteImpl(apiService, driverPostMapper)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideRemotePlaceMapper(): com.badcompany.remote.mapper.PlaceMapper {
        return com.badcompany.remote.mapper.PlaceMapper()
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideRemoteDriverPostMapper(): com.badcompany.remote.mapper.DriverPostMapper {
        return com.badcompany.remote.mapper.DriverPostMapper()
    }


}