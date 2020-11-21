package com.badcompany.data.source

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.CarColorEntity
import com.badcompany.data.model.CarDetailsEntity
import com.badcompany.data.model.CarEntity
import com.badcompany.data.model.CarModelEntity
import com.badcompany.data.repository.CarDataStore
import com.badcompany.data.repository.CarRemote
import com.badcompany.data.repository.UserDataStore
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

    override suspend fun createCar(car: CarEntity): ResultWrapper<String> {
        return carRemote.createCar(car)
    }

    override suspend fun updateCar(car: CarEntity): ResultWrapper<String> {
        return carRemote.updateCar(car)
    }

    override suspend fun deleteCar(id: Long): ResultWrapper<String> {
        return carRemote.deleteCar(id)
    }

    override suspend fun setDefaultCar(id: Long): ResultWrapper<String> {
        return carRemote.setDefaultCar(id)
    }

}