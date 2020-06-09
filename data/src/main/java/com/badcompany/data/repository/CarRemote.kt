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

    suspend fun getCars(token: String): ResultWrapper<List<CarDetailsEntity>>
    suspend fun getCarModels(token: String): ResultWrapper<List<CarModelEntity>>
    suspend fun getCarColors(token: String): ResultWrapper<List<CarColorEntity>>
    suspend fun createCar(token: String, car: CarEntity): ResultWrapper<String>
    suspend fun deleteCar(token: String, id: Long): ResultWrapper<String>
    suspend fun updateCar(token: String, car: CarEntity): ResultWrapper<String>
    suspend fun setDefaultCar(token: String, id: Long): ResultWrapper<String>
}