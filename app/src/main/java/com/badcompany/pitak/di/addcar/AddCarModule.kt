package com.badcompany.pitak.di.addcar

import com.badcompany.data.CarRepositoryImpl
import com.badcompany.data.mapper.CarColorMapper
import com.badcompany.data.mapper.CarModelMapper
import com.badcompany.data.mapper.CarPhotoMapper
import com.badcompany.data.repository.CarRemote
import com.badcompany.data.source.CarDataStoreFactory
import com.badcompany.data.source.CarRemoteDataStore
import com.badcompany.domain.repository.CarRepository
import com.badcompany.domain.usecases.GetCarColors
import com.badcompany.domain.usecases.GetCarModels
import com.badcompany.domain.usecases.UploadCarPhoto
import com.badcompany.remote.ApiService
import com.badcompany.remote.CarRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object AddCarModule {


    @AddCarScope
    @Provides
    @JvmStatic
    fun provideUploadCarPhotoUseCase(carRepository: CarRepository): UploadCarPhoto {
        return UploadCarPhoto(carRepository)
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
                             photoMapper: CarPhotoMapper,
                             modelMapper: CarModelMapper,
                             colorMapper: CarColorMapper): CarRepository {
        return CarRepositoryImpl(carDataStoreFactory, photoMapper, colorMapper, modelMapper)
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideCarPhotoMapper(): CarPhotoMapper {
        return CarPhotoMapper()
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
                         carPhotoMapper: com.badcompany.remote.mapper.CarPhotoMapper,
                         carModelMapper: com.badcompany.remote.mapper.CarModelMapper,
                         carColorMapper: com.badcompany.remote.mapper.CarColorMapper): CarRemote {
        return CarRemoteImpl(apiService, carPhotoMapper, carModelMapper, carColorMapper)
    }

    @AddCarScope
    @Provides
    @JvmStatic
    fun provideRemoteCarPhotoMapper(): com.badcompany.remote.mapper.CarPhotoMapper {
        return com.badcompany.remote.mapper.CarPhotoMapper()
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

}