package com.badcompany.data

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.mapper.CarColorMapper
import com.badcompany.data.mapper.CarDetailsMapper
import com.badcompany.data.mapper.CarMapper
import com.badcompany.data.mapper.CarModelMapper
import com.badcompany.data.source.CarDataStoreFactory
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.repository.CarRepository
import com.badcompany.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class CarRepositoryImpl @Inject constructor(private val factory: CarDataStoreFactory,
                                            private val colorMapper: CarColorMapper,
                                            private val modelMapper: CarModelMapper,
                                            private val carMapper: CarMapper,
                                            private val carDetailsMapper: CarDetailsMapper
) : CarRepository {

    override suspend fun getCars(): ResultWrapper<List<CarDetails>> {
        val response = factory.retrieveDataStore(false).getCars()
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val newCars = ArrayList<CarDetails>()
                response.value.forEach {
                    newCars.add(carDetailsMapper.mapFromEntity(it))
                }
                ResultWrapper.Success(newCars)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


    override suspend fun getCarModels(): ResultWrapper<List<CarModelBody>> {
        val response = factory.retrieveDataStore(false)
            .getCarModels()
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val newCarColors = ArrayList<CarModelBody>()
                response.value.forEach {
                    newCarColors.add(modelMapper.mapFromEntity(it))
                }
                ResultWrapper.Success(newCarColors)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getCarColors(): ResultWrapper<List<CarColorBody>> {
        val response = factory.retrieveDataStore(false)            .getCarColors()
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val newCarColors = ArrayList<CarColorBody>()
                response.value.forEach {
                    newCarColors.add(colorMapper.mapFromEntity(it))
                }
                ResultWrapper.Success(newCarColors)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun createCar(car: Car): ResultWrapper<String> {
        val response = factory.retrieveDataStore(false)
            .createCar(carMapper.mapToEntity(car))
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success("SUCCESS")
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun updateCar(car: Car): ResultWrapper<String> {
        val response = factory.retrieveDataStore(false)
            .updateCar(carMapper.mapToEntity(car))
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success("SUCCESS")
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun deleteCar(id: Long): ResultWrapper<String> {
        val response = factory.retrieveDataStore(false)
            .deleteCar(id)
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success("SUCCESS")
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun setDefaultCar(id: Long): ResultWrapper<String> {
        val response = factory.retrieveDataStore(false)
            .setDefaultCar(id)
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success("SUCCESS")
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


}