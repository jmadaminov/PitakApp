package com.badcompany.pitak.di.addcar

import com.badcompany.data.CarRepositoryImpl
import com.badcompany.data.mapper.CarColorMapper
import com.badcompany.data.mapper.CarMapper
import com.badcompany.data.mapper.CarModelMapper
import com.badcompany.data.repository.CarRemote
import com.badcompany.data.source.CarDataStoreFactory
import com.badcompany.data.source.CarRemoteDataStore
import com.badcompany.domain.repository.CarRepository
import com.badcompany.domain.usecases.GetCarColors
import com.badcompany.domain.usecases.GetCarModels
import com.badcompany.domain.usecases.SaveCar
import com.badcompany.domain.usecases.SetDefaultCar
import com.badcompany.remote.ApiService
import com.badcompany.remote.CarRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object AddCarModule {


    @AddCarScope
    @Provides
    @JvmStatic
    fun provideSaveCar(carRepository: CarRepository): SaveCar {
        return SaveCar(carRepository)
    }


    @AddCarScope
    @Provides
    @JvmStatic
    fun provideSetDefaultCar(carRepository: CarRepository): SetDefaultCar {
        return SetDefaultCar(carRepository)
    }


    @AddCarScope
    @Provides
    @JvmStatic
    fun provideGetCarColorsUseCase(carRepository: CarRepository): GetCarColors {
        return GetCarColors(carRepository)
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideGetCarModelsUseCase(carRepository: CarRepository): GetCarModels {
        return GetCarModels(carRepository)
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideCarRepository(carDataStoreFactory: CarDataStoreFactory,
                             modelMapper: CarModelMapper,
                             colorMapper: CarColorMapper,
                             carMapper: CarMapper): CarRepository {
        return CarRepositoryImpl(carDataStoreFactory, colorMapper, modelMapper, carMapper)
    }


    @AddCarScope
    @Provides
    @JvmStatic
    fun provideCarColorMapper(): CarColorMapper {
        return CarColorMapper()
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideCarMapper(): CarMapper {
        return CarMapper()
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideCarModelMapper(): CarModelMapper {
        return CarModelMapper()
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideCarDataStoreFactory(carRemoteDataStore: CarRemoteDataStore): CarDataStoreFactory {
        return CarDataStoreFactory(carRemoteDataStore)
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideCarRemoteDataStore(carRemote: CarRemote): CarRemoteDataStore {
        return CarRemoteDataStore(carRemote)
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideCarRemote(apiService: ApiService,
                         carModelMapper: com.badcompany.remote.mapper.CarModelMapper,
                         carColorMapper: com.badcompany.remote.mapper.CarColorMapper,
                         carMapper: com.badcompany.remote.mapper.CarMapper): CarRemote {
        return CarRemoteImpl(apiService, carModelMapper, carColorMapper, carMapper)
    }


    @AddCarScope
    @Provides
    @JvmStatic
    fun provideRemoteCarModelMapper(): com.badcompany.remote.mapper.CarModelMapper {
        return com.badcompany.remote.mapper.CarModelMapper()
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideRemoteCarColorMapper(): com.badcompany.remote.mapper.CarColorMapper {
        return com.badcompany.remote.mapper.CarColorMapper()
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideRemoteCarMapper(): com.badcompany.remote.mapper.CarMapper {
        return com.badcompany.remote.mapper.CarMapper()
    }

}