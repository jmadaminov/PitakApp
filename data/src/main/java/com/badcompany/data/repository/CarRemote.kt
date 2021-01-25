package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.CarColorEntity
import com.badcompany.data.model.CarDetailsEntity
import com.badcompany.data.model.CarEntity
import com.badcompany.data.model.CarModelEntity


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface CarRemote {

    suspend fun getCars(): ResultWrapper<List<CarDetailsEntity>>
    suspend fun getCarModels(): ResultWrapper<List<CarModelEntity>>
    suspend fun getCarColors(): ResultWrapper<List<CarColorEntity>>
    suspend fun createCar( car: CarEntity): ResultWrapper<String>
    suspend fun deleteCar( id: Long): ResultWrapper<List<CarDetailsEntity>>
    suspend fun updateCar( car: CarEntity): ResultWrapper<String>
    suspend fun setDefaultCar( id: Long): ResultWrapper<String>
}