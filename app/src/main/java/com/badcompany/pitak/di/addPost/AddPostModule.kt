package com.badcompany.pitak.di.addPost

import com.badcompany.data.DriverPostRepositoryImpl
import com.badcompany.data.PlaceRepositoryImpl
import com.badcompany.data.mapper.DriverPostMapper
import com.badcompany.data.mapper.PlaceMapper
import com.badcompany.data.repository.PlaceRemote
import com.badcompany.data.repository.DriverPostRemote
import com.badcompany.data.source.DriverPostDataStoreFactory
import com.badcompany.data.source.DriverPostRemoteDataStore
import com.badcompany.data.source.PlaceDataStoreFactory
import com.badcompany.data.source.PlaceRemoteDataStore
import com.badcompany.domain.repository.DriverPostRepository
import com.badcompany.domain.repository.PlaceRepository
import com.badcompany.domain.usecases.CreateDriverPost
import com.badcompany.domain.usecases.GetPlacesFeed
import com.badcompany.remote.ApiService
import com.badcompany.remote.DriverPostRemoteImpl
import com.badcompany.remote.PlaceRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object AddPostModule {

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideCreateDriverPost(driverPostRepository: DriverPostRepository): CreateDriverPost {
        return CreateDriverPost(driverPostRepository)
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideDriverPostRepository(factory: DriverPostDataStoreFactory,
                                    driverPostMapper: DriverPostMapper): DriverPostRepository {
        return DriverPostRepositoryImpl(factory, driverPostMapper)
    }


    @Provides
    @AddPostScope
    @JvmStatic
    fun provideDriverPostMapper(): DriverPostMapper {
        return DriverPostMapper()
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideGetPlacesFeed(placeRepository: PlaceRepository): GetPlacesFeed {
        return GetPlacesFeed(placeRepository)
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun providePlaceRepository(factory: PlaceDataStoreFactory,
                               placeMapper: PlaceMapper): PlaceRepository {
        return PlaceRepositoryImpl(factory, placeMapper)
    }


    @Provides
    @AddPostScope
    @JvmStatic
    fun providePlaceMapper(): PlaceMapper {
        return PlaceMapper()
    }


    @Provides
    @AddPostScope
    @JvmStatic
    fun providePlaceDataStoreFactory(placeRemoteDataStore: PlaceRemoteDataStore): PlaceDataStoreFactory {
        return PlaceDataStoreFactory(placeRemoteDataStore)
    }

    @Provides
    @AddPostScope
    @JvmStatic
    fun provideDriverPostDataStoreFactory(driverPostRemoteDataStore: DriverPostRemoteDataStore): DriverPostDataStoreFactory {
        return DriverPostDataStoreFactory(driverPostRemoteDataStore)
    }

    @Provides
    @AddPostScope
    @JvmStatic
    fun providePlaceRemoteDataStore(placeRemote: PlaceRemote): PlaceRemoteDataStore {
        return PlaceRemoteDataStore(placeRemote)
    }

    @Provides
    @AddPostScope
    @JvmStatic
    fun provideDriverPostRemoteDataStore(driverDriverPostRemote: DriverPostRemote): DriverPostRemoteDataStore {
        return DriverPostRemoteDataStore(driverDriverPostRemote)
    }


    @Provides
    @AddPostScope
    @JvmStatic
    fun providePlaceRemote(apiService: ApiService,
                           placeMapper: com.badcompany.remote.mapper.PlaceMapper): PlaceRemote {
        return PlaceRemoteImpl(apiService, placeMapper)
    }


    @Provides
    @AddPostScope
    @JvmStatic
    fun provideDriverPostRemote(apiService: ApiService,
                                driverPostMapper: com.badcompany.remote.mapper.DriverPostMapper): DriverPostRemote {
        return DriverPostRemoteImpl(apiService, driverPostMapper)
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideRemotePlaceMapper(): com.badcompany.remote.mapper.PlaceMapper {
        return com.badcompany.remote.mapper.PlaceMapper()
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideRemoteDriverPostMapper(): com.badcompany.remote.mapper.DriverPostMapper {
        return com.badcompany.remote.mapper.DriverPostMapper()
    }


}