package com.novatec.data.source

import com.novatec.core.ResultWrapper
import com.novatec.data.model.CarColorEntity
import com.novatec.data.model.CarDetailsEntity
import com.novatec.data.model.CarEntity
import com.novatec.data.model.CarModelEntity
import com.novatec.data.repository.CarDataStore
import com.novatec.data.repository.CarRemote
import com.novatec.data.repository.UserDataStore
import javax.inject.Inject

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class CarRemoteDataStore @Inject constructor(private val carRemote: CarRemote) :
    CarDataStore {
    override suspend fun getCars(): ResultWrapper<List<CarDetailsEntity>> {
        return carRemote.getCars()
    }

    override suspend fun getCarModels(): ResultWrapper<List<CarModelEntity>> {
        return carRemote.getCarModels()
    }

    override suspend fun getCarColors(): ResultWrapper<List<CarColorEntity>> {
        return carRemote.getCarColors()
    }

    override suspend fun createCar(car: CarEntity): ResultWrapper<CarEntity> {
        return carRemote.createCar(car)
    }

    override suspend fun updateCar(car: CarEntity): ResultWrapper<CarEntity> {
        return carRemote.updateCar(car)
    }

    override suspend fun deleteCar(id: Long): ResultWrapper<List<CarDetailsEntity>> {
        return carRemote.deleteCar(id)
    }

    override suspend fun setDefaultCar(id: Long): ResultWrapper<String> {
        return carRemote.setDefaultCar(id)
    }

}