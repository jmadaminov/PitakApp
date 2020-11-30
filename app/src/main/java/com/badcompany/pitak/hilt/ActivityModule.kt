package com.badcompany.pitak.hilt

import com.badcompany.pitak.BuildConfig
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.remote.ApiServiceFactory
import com.badcompany.remote.AuthorizedApiService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import splitties.experimental.ExperimentalSplittiesApi
import com.badcompany.data.*
import com.badcompany.data.mapper.*
import com.badcompany.data.repository.*
import com.badcompany.data.source.*
import com.badcompany.domain.repository.*
import com.badcompany.domain.usecases.*
import com.badcompany.remote.*
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideGetCars(carRepository: CarRepository): GetCars {
        return GetCars(carRepository)
    }

    @Provides
    fun provideDeleteCar(carRepository: CarRepository): DeleteCar {
        return DeleteCar(carRepository)
    }

    @Provides
    fun provideSaveCar(carRepository: CarRepository): SaveCar {
        return SaveCar(carRepository)
    }


    @Provides
    fun provideCreateDriverPost(driverPostRepository: DriverPostRepository): CreateDriverPost {
        return CreateDriverPost(driverPostRepository)
    }

    @Provides
    fun provideSetDefaultCar(carRepository: CarRepository): SetDefaultCar {
        return SetDefaultCar(carRepository)
    }


    @Provides
    fun provideGetCarColorsUseCase(carRepository: CarRepository): GetCarColors {
        return GetCarColors(carRepository)
    }


    @Provides
    fun provideGetCarModelsUseCase(carRepository: CarRepository): GetCarModels {
        return GetCarModels(carRepository)
    }

    @Provides
    fun provideCarRepository(carDataStoreFactory: CarDataStoreFactory,
                             modelMapper: CarModelMapper,
                             colorMapper: CarColorMapper,
                             carMapper: CarMapper,
                             carDetailsMapper: CarDetailsMapper): CarRepository {
        return CarRepositoryImpl(carDataStoreFactory,
                                 colorMapper,
                                 modelMapper,
                                 carMapper,
                                 carDetailsMapper)
    }

    @Provides
    
    fun provideCarColorMapper(): CarColorMapper {
        return CarColorMapper()
    }

    @Provides
    
    fun provideCarDetailsMapper(): CarDetailsMapper {
        return CarDetailsMapper()
    }


    @Provides
    
    fun provideCarMapper(): CarMapper {
        return CarMapper()
    }


    @Provides
    
    fun provideCarModelMapper(): CarModelMapper {
        return CarModelMapper()
    }


    @Provides
    
    fun provideCarDataStoreFactory(carRemoteDataStore: CarRemoteDataStore): CarDataStoreFactory {
        return CarDataStoreFactory(carRemoteDataStore)
    }


    @Provides
    
    fun provideCarRemoteDataStore(carRemote: CarRemote): CarRemoteDataStore {
        return CarRemoteDataStore(carRemote)
    }


    @Provides
    
    fun provideCarRemote(apiService: AuthorizedApiService,
                         carModelMapper: com.badcompany.remote.mapper.CarModelMapper,
                         carColorMapper: com.badcompany.remote.mapper.CarColorMapper,
                         carMapper: com.badcompany.remote.mapper.CarMapper,
                         carDetailsMapper: com.badcompany.remote.mapper.CarDetailsMapper): CarRemote {
        return CarRemoteImpl(apiService,
                             carModelMapper,
                             carColorMapper,
                             carMapper,
                             carDetailsMapper)
    }


    @Provides
    
    fun provideRemoteCarModelMapper(): com.badcompany.remote.mapper.CarModelMapper {
        return com.badcompany.remote.mapper.CarModelMapper()
    }


    @Provides
    
    fun provideRemoteCarColorMapper(): com.badcompany.remote.mapper.CarColorMapper {
        return com.badcompany.remote.mapper.CarColorMapper()
    }


    @Provides
    
    fun provideRemoteCarMapper(): com.badcompany.remote.mapper.CarMapper {
        return com.badcompany.remote.mapper.CarMapper()
    }

    
    @Provides
    fun provideRemoteCarDetailsMapper(): com.badcompany.remote.mapper.CarDetailsMapper {
        return com.badcompany.remote.mapper.CarDetailsMapper()
    }

    
    @Provides
    fun provideUploadPhoto(repository: FileUploadRepository): UploadPhoto {
        return UploadPhoto(repository)
    }

    
    @Provides
    fun provideFileUploadRepository(factory: FileUploadDataStoreFactory,
                                    photoMapper: PhotoMapper): FileUploadRepository {
        return FileUploadRepositoryImpl(factory, photoMapper)
    }

    
    @Provides
    fun provideFileUploadDataStoreFactory(dataStore: FileUploadRemoteDataStore): FileUploadDataStoreFactory {
        return FileUploadDataStoreFactory(dataStore)
    }

    
    @Provides
    fun provideFileUploadRemoteDataStore(remoteDS: FileUploadRemote): FileUploadRemoteDataStore {
        return FileUploadRemoteDataStore(remoteDS)
    }

    
    @Provides
    fun provideFileUploadRemote(apiService: ApiService,
                                photoMapper: com.badcompany.remote.mapper.PhotoMapper): FileUploadRemote {
        return FileUploadRemoteImpl(apiService, photoMapper)
    }

    
    @Provides
    fun providePhotoMapper(): PhotoMapper {
        return PhotoMapper()
    }

    
    @Provides
    fun provideRemotePhotoMapper(): com.badcompany.remote.mapper.PhotoMapper {
        return com.badcompany.remote.mapper.PhotoMapper()
    }


    
    @Provides
    fun provideGetPlacesFeed(placeRepository: PlaceRepository): GetPlacesFeed {
        return GetPlacesFeed(placeRepository)
    }


    @Provides
    
    fun providePlaceRepository(factory: PlaceDataStoreFactory,
                               placeMapper: PlaceMapper): PlaceRepository {
        return PlaceRepositoryImpl(factory, placeMapper)
    }


    @Provides
    
    fun provideGetPassengerPostWithFilter(passengerPostRepository: PassengerPostRepository): GetPassengerPostWithFilter {
        return GetPassengerPostWithFilter(passengerPostRepository)
    }


    @Provides
    
    fun providePassengerPostRepository(factory: PassengerPostDataStoreFactory,
                                       driverPostMapper: PassengerPostMapper,
                                       filterMapper: FilterMapper,
                                       driverOfferMapper: DriverOfferMapper): PassengerPostRepository {
        return PassengerPostRepositoryImpl(factory,
                                           driverPostMapper,
                                           filterMapper,
                                           driverOfferMapper)
    }

    @Provides
    
    fun providePassengerPostDataStoreFactory(postDataStore: PassengerPostDataStore): PassengerPostDataStoreFactory {
        return PassengerPostDataStoreFactory(postDataStore)
    }

    @Provides

    
    fun providePassengerPostDataStore(passengerPostRemote: PassengerPostRemote): PassengerPostDataStore {
        return PassengerPostRemoteDataStore(passengerPostRemote)
    }

    @Provides

    
    fun providePassengerPostRemote(apiService: AuthorizedApiService,
                                   postMapper: com.badcompany.remote.mapper.PassengerPostMapper,
                                   filterMapper: com.badcompany.remote.mapper.FilterMapper,
                                   driverOfferMapper: com.badcompany.remote.mapper.DriverOfferMapper): PassengerPostRemote {
        return PassengerPostRemoteImpl(apiService, postMapper, filterMapper, driverOfferMapper)
    }


    @Provides
    
    fun provideRemotePassengerPostMapper(): com.badcompany.remote.mapper.PassengerPostMapper {
        return com.badcompany.remote.mapper.PassengerPostMapper()
    }


    @Provides
    
    fun provideRemoteFilterMapper(): com.badcompany.remote.mapper.FilterMapper {
        return com.badcompany.remote.mapper.FilterMapper()
    }

    @Provides
    
    fun provideRemoteDriverOfferMapper(): com.badcompany.remote.mapper.DriverOfferMapper {
        return com.badcompany.remote.mapper.DriverOfferMapper()
    }


    @Provides
    
    fun provideGetActiveDriverPost(driverPostRepository: DriverPostRepository): GetActiveDriverPost {
        return GetActiveDriverPost(driverPostRepository)
    }


    @Provides
    
    fun provideDeleteDriverPost(driverPostRepository: DriverPostRepository): DeleteDriverPost {
        return DeleteDriverPost(driverPostRepository)
    }


    @Provides
    
    fun provideFinishDriverPost(driverPostRepository: DriverPostRepository): FinishDriverPost {
        return FinishDriverPost(driverPostRepository)
    }


    @Provides
    
    fun provideGetHistoryDriverPost(driverPostRepository: DriverPostRepository): GetHistoryDriverPost {
        return GetHistoryDriverPost(driverPostRepository)
    }


    @Provides
    
    fun provideDriverPostRepository(factory: DriverPostDataStoreFactory,
                                    driverPostMapper: DriverPostMapper): DriverPostRepository {
        return DriverPostRepositoryImpl(factory, driverPostMapper)
    }

    @Provides
    
    fun providePlaceMapper(): PlaceMapper {
        return PlaceMapper()
    }

    @Provides
    
    fun provideDriverPostMapper(): DriverPostMapper {
        return DriverPostMapper()
    }

    @Provides
    
    fun provideDriverOfferMapper(): DriverOfferMapper {
        return DriverOfferMapper()
    }

    @Provides
    
    fun providePlaceDataStoreFactory(placeRemoteDataStore: PlaceRemoteDataStore): PlaceDataStoreFactory {
        return PlaceDataStoreFactory(placeRemoteDataStore)
    }

    @Provides

    
    fun provideDriverPostDataStoreFactory(driverPostRemoteDataStore: DriverPostRemoteDataStore): DriverPostDataStoreFactory {
        return DriverPostDataStoreFactory(driverPostRemoteDataStore)
    }

    @Provides

    
    fun providePlaceRemoteDataStore(placeRemote: PlaceRemote): PlaceRemoteDataStore {
        return PlaceRemoteDataStore(placeRemote)
    }

    @Provides

    
    fun provideDriverPostRemoteDataStore(driverDriverPostRemote: DriverPostRemote): DriverPostRemoteDataStore {
        return DriverPostRemoteDataStore(driverDriverPostRemote)
    }


    @Provides
    fun providePlaceRemote(apiService: AuthorizedApiService,
                           placeMapper: com.badcompany.remote.mapper.PlaceMapper): PlaceRemote {
        return PlaceRemoteImpl(apiService, placeMapper)
    }


    @Provides
    fun provideDriverPostRemote(apiService: AuthorizedApiService,
                                driverPostMapper: com.badcompany.remote.mapper.DriverPostMapper): DriverPostRemote {
        return DriverPostRemoteImpl(apiService, driverPostMapper)
    }


    @Provides
    fun provideRemotePlaceMapper(): com.badcompany.remote.mapper.PlaceMapper {
        return com.badcompany.remote.mapper.PlaceMapper()
    }


    @Provides
    fun provideRemoteDriverPostMapper(): com.badcompany.remote.mapper.DriverPostMapper {
        return com.badcompany.remote.mapper.DriverPostMapper()
    }



    @ExperimentalSplittiesApi
    @Provides
    fun provideAuthorizedApiService(): AuthorizedApiService {
        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG,
                                                          AppPreferences.token,
                                                          AppPreferences.language)
    }


}


