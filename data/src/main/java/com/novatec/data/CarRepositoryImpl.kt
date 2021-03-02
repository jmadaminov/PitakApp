package com.novatec.data

import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.mapper.CarColorMapper
import com.novatec.data.mapper.CarDetailsMapper
import com.novatec.data.mapper.CarMapper
import com.novatec.data.mapper.CarModelMapper
import com.novatec.data.source.CarDataStoreFactory
import com.novatec.domain.domainmodel.Car
import com.novatec.domain.domainmodel.CarColorBody
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.domain.domainmodel.CarModelBody
import com.novatec.domain.repository.CarRepository
import com.novatec.domain.repository.UserRepository
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
            is ErrorWrapper.RespError -> response
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
            is ErrorWrapper.RespError -> response
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
        val response = factory.retrieveDataStore(false).getCarColors()
        return when (response) {
            is ErrorWrapper.RespError -> response
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

    override suspend fun createCar(car: Car): ResultWrapper<Car> {
        val response = factory.retrieveDataStore(false)
            .createCar(carMapper.mapToEntity(car))
        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(carMapper.mapFromEntity(response.value))
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun updateCar(car: Car): ResultWrapper<Car> {
        val response = factory.retrieveDataStore(false)
            .updateCar(carMapper.mapToEntity(car))
        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(carMapper.mapFromEntity(response.value))
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun deleteCar(id: Long): ResultWrapper<List<CarDetails>> {
        val response = factory.retrieveDataStore(false)
            .deleteCar(id)
        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val cars = arrayListOf<CarDetails>()
                response.value.forEach { cars.add(carDetailsMapper.mapFromEntity(it)) }
                ResultWrapper.Success(cars)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun setDefaultCar(id: Long): ResultWrapper<String> {
        val response = factory.retrieveDataStore(false)
            .setDefaultCar(id)
        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success("SUCCESS")
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


}