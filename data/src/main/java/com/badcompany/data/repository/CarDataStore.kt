package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.*
import java.io.File


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface CarDataStore {
    suspend fun getCars(token: String): ResultWrapper<List<CarDetailsEntity>>
    suspend fun getCarModels(token: String, lang:String): ResultWrapper<List<CarModelEntity>>
    suspend fun getCarColors(token: String, lang:String): ResultWrapper<List<CarColorEntity>>
    suspend fun createCar(token: String, car: CarEntity): ResultWrapper<String>
    suspend fun updateCar(token: String, car: CarEntity): ResultWrapper<String>
    suspend fun deleteCar(token: String, id: Long): ResultWrapper<String>
    suspend fun setDefaultCar(token: String, id: Long): ResultWrapper<String>
}