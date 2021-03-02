package com.novatec.data.repository

import com.novatec.core.ResultWrapper
import com.novatec.data.model.*
import java.io.File


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface CarDataStore {
    suspend fun getCars(): ResultWrapper<List<CarDetailsEntity>>
    suspend fun getCarModels(): ResultWrapper<List<CarModelEntity>>
    suspend fun getCarColors(): ResultWrapper<List<CarColorEntity>>
    suspend fun createCar( car: CarEntity): ResultWrapper<CarEntity>
    suspend fun updateCar( car: CarEntity): ResultWrapper<CarEntity>
    suspend fun deleteCar( id: Long): ResultWrapper<List<CarDetailsEntity>>
    suspend fun setDefaultCar( id: Long): ResultWrapper<String>
}