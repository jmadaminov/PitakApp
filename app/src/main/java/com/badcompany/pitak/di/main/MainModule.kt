package com.badcompany.pitak.di.main

import com.badcompany.data.DriverPostRepositoryImpl
import com.badcompany.data.PassengerPostRepositoryImpl
import com.badcompany.data.mapper.DriverPostMapper
import com.badcompany.data.mapper.FilterMapper
import com.badcompany.data.mapper.PassengerPostMapper
import com.badcompany.data.mapper.PlaceMapper
import com.badcompany.data.repository.DriverPostRemote
import com.badcompany.data.repository.PassengerPostDataStore
import com.badcompany.data.repository.PassengerPostRemote
import com.badcompany.data.repository.PlaceRemote
import com.badcompany.data.source.*
import com.badcompany.domain.repository.DriverPostRepository
import com.badcompany.domain.repository.PassengerPostRepository
import com.badcompany.domain.usecases.*
import com.badcompany.remote.ApiService
import com.badcompany.remote.DriverPostRemoteImpl
import com.badcompany.remote.PassengerPostRemoteImpl
import com.badcompany.remote.PlaceRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object MainModule {


    @MainScope
    @Provides
    @JvmStatic
    fun provideGetPassengerPostWithFilter(passengerPostRepository: PassengerPostRepository): GetPassengerPostWithFilter {
        return GetPassengerPostWithFilter(passengerPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun providePassengerPostRepository(factory: PassengerPostDataStoreFactory,
                                       driverPostMapper: PassengerPostMapper,
                                       filterMapper: FilterMapper): PassengerPostRepository {
        return PassengerPostRepositoryImpl(factory, driverPostMapper, filterMapper)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePassengerPostDataStoreFactory(postDataStore: PassengerPostDataStore): PassengerPostDataStoreFactory {
        return PassengerPostDataStoreFactory(postDataStore)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePassengerPostDataStore(passengerPostRemote: PassengerPostRemote): PassengerPostDataStore {
        return PassengerPostRemoteDataStore(passengerPostRemote)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePassengerPostRemote(apiService: ApiService,
                                   postMapper: com.badcompany.remote.mapper.PassengerPostMapper,
                                   filterMapper: com.badcompany.remote.mapper.FilterMapper): PassengerPostRemote {
        return PassengerPostRemoteImpl(apiService, postMapper, filterMapper)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideRemotePassengerPostMapper(): com.badcompany.remote.mapper.PassengerPostMapper {
        return com.badcompany.remote.mapper.PassengerPostMapper()
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideRemoteFilterMapper(): com.badcompany.remote.mapper.FilterMapper {
        return com.badcompany.remote.mapper.FilterMapper()
    }


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