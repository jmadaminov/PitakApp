package com.novatec.epitak.hilt

import com.novatec.data.*
import com.novatec.data.mapper.*
import com.novatec.data.repository.*
import com.novatec.data.source.*
import com.novatec.domain.repository.*
import com.novatec.domain.usecases.*
import com.novatec.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ActivityModule {


    @Provides
    fun provideLogUserInUseCase(userRepository: UserRepository): LogUserIn {
        return LogUserIn(userRepository)
    }

    @Provides
    fun provideSmsConfirm(userRepository: UserRepository): SmsConfirm {
        return SmsConfirm(userRepository)
    }

    @Provides
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUser {
        return RegisterUser(userRepository)
    }


    @Provides
    fun provideUserRepository(factory: UserDataStoreFactory,
                              userMapper: UserMapper,
                              userCredentialsMapper: UserCredentialsMapper,
                              authMapper: AuthMapper): UserRepository {
        return UserRepositoryImpl(factory, userMapper, userCredentialsMapper, authMapper)
    }

    @Provides
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Provides
    fun provideAuthMapper(): AuthMapper {
        return AuthMapper()
    }

    @Provides
    fun provideUserCredentialsMapper(): UserCredentialsMapper {
        return UserCredentialsMapper()
    }

    @Provides
    fun provideUserDataStoreFactory(userRemoteDataStore: UserRemoteDataStore): UserDataStoreFactory {
        return UserDataStoreFactory(userRemoteDataStore)
    }

    @Provides
    fun provideUserRemoteDataStore(userRemote: UserRemote): UserRemoteDataStore {
        return UserRemoteDataStore(userRemote)
    }

    @Provides
    fun provideUserRemote(apiService: ApiService,
                          userCredMapper: com.novatec.remote.mapper.UserCredentialsMapper,
                          userMapper: com.novatec.remote.mapper.UserMapper,
                          authMapper: com.novatec.remote.mapper.AuthMapper,
                          authApiService: AuthorizedApiService): UserRemote {
        return UserRemoteImpl(apiService, authApiService, userCredMapper, userMapper, authMapper)
    }


    @Provides
    fun provideRemoteUserCredentialsMapper(): com.novatec.remote.mapper.UserCredentialsMapper {
        return com.novatec.remote.mapper.UserCredentialsMapper()
    }


    @Provides
    fun provideRemoteUserMapper(): com.novatec.remote.mapper.UserMapper {
        return com.novatec.remote.mapper.UserMapper()
    }


    @Provides
    fun provideRemoteAuthMapper(): com.novatec.remote.mapper.AuthMapper {
        return com.novatec.remote.mapper.AuthMapper()
    }


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
                         carModelMapper: com.novatec.remote.mapper.CarModelMapper,
                         carColorMapper: com.novatec.remote.mapper.CarColorMapper,
                         carMapper: com.novatec.remote.mapper.CarMapper,
                         carDetailsMapper: com.novatec.remote.mapper.CarDetailsMapper): CarRemote {
        return CarRemoteImpl(apiService,
                             carModelMapper,
                             carColorMapper,
                             carMapper,
                             carDetailsMapper)
    }


    @Provides
    fun provideRemoteCarModelMapper(): com.novatec.remote.mapper.CarModelMapper {
        return com.novatec.remote.mapper.CarModelMapper()
    }


    @Provides
    fun provideRemoteCarColorMapper(): com.novatec.remote.mapper.CarColorMapper {
        return com.novatec.remote.mapper.CarColorMapper()
    }


    @Provides
    fun provideRemoteCarMapper(): com.novatec.remote.mapper.CarMapper {
        return com.novatec.remote.mapper.CarMapper()
    }


    @Provides
    fun provideRemoteCarDetailsMapper(): com.novatec.remote.mapper.CarDetailsMapper {
        return com.novatec.remote.mapper.CarDetailsMapper()
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
                                photoMapper: com.novatec.remote.mapper.PhotoMapper): FileUploadRemote {
        return FileUploadRemoteImpl(apiService, photoMapper)
    }


    @Provides
    fun providePhotoMapper(): PhotoMapper {
        return PhotoMapper()
    }


    @Provides
    fun provideRemotePhotoMapper(): com.novatec.remote.mapper.PhotoMapper {
        return com.novatec.remote.mapper.PhotoMapper()
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
                                   postMapper: com.novatec.remote.mapper.PassengerPostMapper,
                                   filterMapper: com.novatec.remote.mapper.FilterMapper,
                                   driverOfferMapper: com.novatec.remote.mapper.DriverOfferMapper): PassengerPostRemote {
        return PassengerPostRemoteImpl(apiService, postMapper, filterMapper, driverOfferMapper)
    }


    @Provides

    fun provideRemotePassengerPostMapper(): com.novatec.remote.mapper.PassengerPostMapper {
        return com.novatec.remote.mapper.PassengerPostMapper()
    }


    @Provides

    fun provideRemoteFilterMapper(): com.novatec.remote.mapper.FilterMapper {
        return com.novatec.remote.mapper.FilterMapper()
    }

    @Provides

    fun provideRemoteDriverOfferMapper(): com.novatec.remote.mapper.DriverOfferMapper {
        return com.novatec.remote.mapper.DriverOfferMapper()
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
                           placeMapper: com.novatec.remote.mapper.PlaceMapper): PlaceRemote {
        return PlaceRemoteImpl(apiService, placeMapper)
    }


    @Provides
    fun provideDriverPostRemote(apiService: AuthorizedApiService,
                                driverPostMapper: com.novatec.remote.mapper.DriverPostMapper): DriverPostRemote {
        return DriverPostRemoteImpl(apiService, driverPostMapper)
    }


    @Provides
    fun provideRemotePlaceMapper(): com.novatec.remote.mapper.PlaceMapper {
        return com.novatec.remote.mapper.PlaceMapper()
    }


    @Provides
    fun provideRemoteDriverPostMapper(): com.novatec.remote.mapper.DriverPostMapper {
        return com.novatec.remote.mapper.DriverPostMapper()
    }


}


