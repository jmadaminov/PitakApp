package com.badcompany.pitak.hilt
//import com.example.benefit.ui.auth.LoginFragmentFactory
import com.badcompany.data.*
import com.badcompany.data.mapper.*
import com.badcompany.data.repository.*
import com.badcompany.data.source.*
import com.badcompany.domain.repository.*
import com.badcompany.domain.usecases.*
import com.badcompany.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetCars(carRepository: CarRepository): GetCars {
        return GetCars(carRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteCar(carRepository: CarRepository): DeleteCar {
        return DeleteCar(carRepository)
    }

    @Provides
    @Singleton
    fun provideSaveCar(carRepository: CarRepository): SaveCar {
        return SaveCar(carRepository)
    }


    @Provides
    @Singleton
    fun provideCreateDriverPost(driverPostRepository: DriverPostRepository): CreateDriverPost {
        return CreateDriverPost(driverPostRepository)
    }

    @Provides
    @Singleton
    fun provideSetDefaultCar(carRepository: CarRepository): SetDefaultCar {
        return SetDefaultCar(carRepository)
    }


    @Provides
    @Singleton
    fun provideGetCarColorsUseCase(carRepository: CarRepository): GetCarColors {
        return GetCarColors(carRepository)
    }


    @Provides
    @Singleton
    fun provideGetCarModelsUseCase(carRepository: CarRepository): GetCarModels {
        return GetCarModels(carRepository)
    }

    @Provides
    @Singleton
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
    @Singleton
    fun provideCarColorMapper(): CarColorMapper {
        return CarColorMapper()
    }

    @Provides
    @Singleton
    fun provideCarDetailsMapper(): CarDetailsMapper {
        return CarDetailsMapper()
    }


    @Provides
    @Singleton
    fun provideCarMapper(): CarMapper {
        return CarMapper()
    }


    @Provides
    @Singleton
    fun provideCarModelMapper(): CarModelMapper {
        return CarModelMapper()
    }


    @Provides
    @Singleton
    fun provideCarDataStoreFactory(carRemoteDataStore: CarRemoteDataStore): CarDataStoreFactory {
        return CarDataStoreFactory(carRemoteDataStore)
    }


    @Provides
    @Singleton
    fun provideCarRemoteDataStore(carRemote: CarRemote): CarRemoteDataStore {
        return CarRemoteDataStore(carRemote)
    }


    @Provides
    @Singleton
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
    @Singleton
    fun provideRemoteCarModelMapper(): com.badcompany.remote.mapper.CarModelMapper {
        return com.badcompany.remote.mapper.CarModelMapper()
    }


    @Provides
    @Singleton
    fun provideRemoteCarColorMapper(): com.badcompany.remote.mapper.CarColorMapper {
        return com.badcompany.remote.mapper.CarColorMapper()
    }


    @Provides
    @Singleton
    fun provideRemoteCarMapper(): com.badcompany.remote.mapper.CarMapper {
        return com.badcompany.remote.mapper.CarMapper()
    }

    @Singleton
    @Provides
    fun provideRemoteCarDetailsMapper(): com.badcompany.remote.mapper.CarDetailsMapper {
        return com.badcompany.remote.mapper.CarDetailsMapper()
    }

    @Singleton
    @Provides
    fun provideUploadPhoto(repository: FileUploadRepository): UploadPhoto {
        return UploadPhoto(repository)
    }

    @Singleton
    @Provides
    fun provideFileUploadRepository(factory: FileUploadDataStoreFactory,
                                    photoMapper: PhotoMapper): FileUploadRepository {
        return FileUploadRepositoryImpl(factory, photoMapper)
    }

    @Singleton
    @Provides
    fun provideFileUploadDataStoreFactory(dataStore: FileUploadRemoteDataStore): FileUploadDataStoreFactory {
        return FileUploadDataStoreFactory(dataStore)
    }

    @Singleton
    @Provides
    fun provideFileUploadRemoteDataStore(remoteDS: FileUploadRemote): FileUploadRemoteDataStore {
        return FileUploadRemoteDataStore(remoteDS)
    }

    @Singleton
    @Provides
    fun provideFileUploadRemote(apiService: ApiService,
                                photoMapper: com.badcompany.remote.mapper.PhotoMapper): FileUploadRemote {
        return FileUploadRemoteImpl(apiService, photoMapper)
    }

    @Singleton
    @Provides
    fun providePhotoMapper(): PhotoMapper {
        return PhotoMapper()
    }

    @Singleton
    @Provides
    fun provideRemotePhotoMapper(): com.badcompany.remote.mapper.PhotoMapper {
        return com.badcompany.remote.mapper.PhotoMapper()
    }


    @Singleton
    @Provides
    fun provideGetPlacesFeed(placeRepository: PlaceRepository): GetPlacesFeed {
        return GetPlacesFeed(placeRepository)
    }


    @Provides
    @Singleton
    fun providePlaceRepository(factory: PlaceDataStoreFactory,
                               placeMapper: PlaceMapper): PlaceRepository {
        return PlaceRepositoryImpl(factory, placeMapper)
    }


    @Provides
    @Singleton
    fun provideGetPassengerPostWithFilter(passengerPostRepository: PassengerPostRepository): GetPassengerPostWithFilter {
        return GetPassengerPostWithFilter(passengerPostRepository)
    }


    @Provides
    @Singleton
    fun providePassengerPostRepository(factory: PassengerPostDataStoreFactory,
                                       driverPostMapper: PassengerPostMapper,
                                       filterMapper: FilterMapper): PassengerPostRepository {
        return PassengerPostRepositoryImpl(factory, driverPostMapper, filterMapper)
    }

    @Provides
    @Singleton
    fun providePassengerPostDataStoreFactory(postDataStore: PassengerPostDataStore): PassengerPostDataStoreFactory {
        return PassengerPostDataStoreFactory(postDataStore)
    }

    @Provides

    @Singleton
    fun providePassengerPostDataStore(passengerPostRemote: PassengerPostRemote): PassengerPostDataStore {
        return PassengerPostRemoteDataStore(passengerPostRemote)
    }

    @Provides

    @Singleton
    fun providePassengerPostRemote(apiService: AuthorizedApiService,
                                   postMapper: com.badcompany.remote.mapper.PassengerPostMapper,
                                   filterMapper: com.badcompany.remote.mapper.FilterMapper): PassengerPostRemote {
        return PassengerPostRemoteImpl(apiService, postMapper, filterMapper)
    }


    @Provides
    @Singleton
    fun provideRemotePassengerPostMapper(): com.badcompany.remote.mapper.PassengerPostMapper {
        return com.badcompany.remote.mapper.PassengerPostMapper()
    }


    @Provides
    @Singleton
    fun provideRemoteFilterMapper(): com.badcompany.remote.mapper.FilterMapper {
        return com.badcompany.remote.mapper.FilterMapper()
    }


    @Provides
    @Singleton
    fun provideGetActiveDriverPost(driverPostRepository: DriverPostRepository): GetActiveDriverPost {
        return GetActiveDriverPost(driverPostRepository)
    }


    @Provides
    @Singleton
    fun provideDeleteDriverPost(driverPostRepository: DriverPostRepository): DeleteDriverPost {
        return DeleteDriverPost(driverPostRepository)
    }


    @Provides
    @Singleton
    fun provideFinishDriverPost(driverPostRepository: DriverPostRepository): FinishDriverPost {
        return FinishDriverPost(driverPostRepository)
    }


    @Provides
    @Singleton
    fun provideGetHistoryDriverPost(driverPostRepository: DriverPostRepository): GetHistoryDriverPost {
        return GetHistoryDriverPost(driverPostRepository)
    }


    @Provides
    @Singleton
    fun provideDriverPostRepository(factory: DriverPostDataStoreFactory,
                                    driverPostMapper: DriverPostMapper): DriverPostRepository {
        return DriverPostRepositoryImpl(factory, driverPostMapper)
    }

    @Provides
    @Singleton
    fun providePlaceMapper(): PlaceMapper {
        return PlaceMapper()
    }

    @Provides

    @Singleton
    fun provideDriverPostMapper(): DriverPostMapper {
        return DriverPostMapper()
    }

    @Provides

    @Singleton
    fun providePlaceDataStoreFactory(placeRemoteDataStore: PlaceRemoteDataStore): PlaceDataStoreFactory {
        return PlaceDataStoreFactory(placeRemoteDataStore)
    }

    @Provides

    @Singleton
    fun provideDriverPostDataStoreFactory(driverPostRemoteDataStore: DriverPostRemoteDataStore): DriverPostDataStoreFactory {
        return DriverPostDataStoreFactory(driverPostRemoteDataStore)
    }

    @Provides

    @Singleton
    fun providePlaceRemoteDataStore(placeRemote: PlaceRemote): PlaceRemoteDataStore {
        return PlaceRemoteDataStore(placeRemote)
    }

    @Provides

    @Singleton
    fun provideDriverPostRemoteDataStore(driverDriverPostRemote: DriverPostRemote): DriverPostRemoteDataStore {
        return DriverPostRemoteDataStore(driverDriverPostRemote)
    }


    @Provides

    @Singleton
    fun providePlaceRemote(apiService: AuthorizedApiService,
                           placeMapper: com.badcompany.remote.mapper.PlaceMapper): PlaceRemote {
        return PlaceRemoteImpl(apiService, placeMapper)
    }


    @Provides

    @Singleton
    fun provideDriverPostRemote(apiService: AuthorizedApiService,
                                driverPostMapper: com.badcompany.remote.mapper.DriverPostMapper): DriverPostRemote {
        return DriverPostRemoteImpl(apiService, driverPostMapper)
    }


    @Provides
    @Singleton
    fun provideRemotePlaceMapper(): com.badcompany.remote.mapper.PlaceMapper {
        return com.badcompany.remote.mapper.PlaceMapper()
    }


    @Provides
    @Singleton
    fun provideRemoteDriverPostMapper(): com.badcompany.remote.mapper.DriverPostMapper {
        return com.badcompany.remote.mapper.DriverPostMapper()
    }


    @Provides
    @Singleton
    fun provideLogUserInUseCase(userRepository: UserRepository): LogUserIn {
        return LogUserIn(userRepository)
    }


    @Provides
    @Singleton
    fun provideSmsConfirm(userRepository: UserRepository): SmsConfirm {
        return SmsConfirm(userRepository)
    }


    @Provides
    @Singleton
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUser {
        return RegisterUser(userRepository)
    }


    @Provides
    @Singleton
    fun provideUserRepository(factory: UserDataStoreFactory,
                              userMapper: UserMapper,
                              userCredentialsMapper: UserCredentialsMapper,
                              authMapper: AuthMapper): UserRepository {
        return UserRepositoryImpl(factory, userMapper, userCredentialsMapper, authMapper)
    }

    @Provides

    @Singleton
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Provides

    @Singleton
    fun provideAuthMapper(): AuthMapper {
        return AuthMapper()
    }

    @Provides

    @Singleton
    fun provideUserCredentialsMapper(): UserCredentialsMapper {
        return UserCredentialsMapper()
    }

    @Provides

    @Singleton
    fun provideUserDataStoreFactory(userRemoteDataStore: UserRemoteDataStore): UserDataStoreFactory {
        return UserDataStoreFactory(userRemoteDataStore)
    }

    @Provides

    @Singleton
    fun provideUserRemoteDataStore(userRemote: UserRemote): UserRemoteDataStore {
        return UserRemoteDataStore(userRemote)
    }


    @Provides

    @Singleton
    fun provideUserRemote(apiService: ApiService,
                          userCredMapper: com.badcompany.remote.mapper.UserCredentialsMapper,
                          userMapper: com.badcompany.remote.mapper.UserMapper,
                          authMapper: com.badcompany.remote.mapper.AuthMapper): UserRemote {
        return UserRemoteImpl(apiService, userCredMapper, userMapper, authMapper)
    }


    @Provides
    @Singleton
    fun provideRemoteUserCredentialsMapper(): com.badcompany.remote.mapper.UserCredentialsMapper {
        return com.badcompany.remote.mapper.UserCredentialsMapper()
    }


    @Provides
    @Singleton
    fun provideRemoteUserMapper(): com.badcompany.remote.mapper.UserMapper {
        return com.badcompany.remote.mapper.UserMapper()
    }


    @Provides
    @Singleton
    fun provideRemoteAuthMapper(): com.badcompany.remote.mapper.AuthMapper {
        return com.badcompany.remote.mapper.AuthMapper()
    }


}




