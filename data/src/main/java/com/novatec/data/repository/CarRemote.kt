package com.novatec.data.repository

import com.novatec.core.ResultWrapper
import com.novatec.data.model.CarColorEntity
import com.novatec.data.model.CarDetailsEntity
import com.novatec.data.model.CarEntity
import com.novatec.data.model.CarModelEntity


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface CarRemote {

    suspend fun getCars(): ResultWrapper<List<CarDetailsEntity>>
    suspend fun getCarModels(): ResultWrapper<List<CarModelEntity>>
    suspend fun getCarColors(): ResultWrapper<List<CarColorEntity>>
    suspend fun createCar( car: CarEntity): ResultWrapper<CarEntity>
    suspend fun deleteCar( id: Long): ResultWrapper<List<CarDetailsEntity>>
    suspend fun updateCar( car: CarEntity): ResultWrapper<CarEntity>
    suspend fun setDefaultCar( id: Long): ResultWrapper<String>
}