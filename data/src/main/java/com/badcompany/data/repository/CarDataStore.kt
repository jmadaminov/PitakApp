package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.CarColorEntity
import com.badcompany.data.model.CarEntity
import com.badcompany.data.model.CarModelEntity
import com.badcompany.data.model.PhotoEntity
import java.io.File


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface CarDataStore {

    suspend fun getCarModels(token: String): ResultWrapper<List<CarModelEntity>>
    suspend fun getCarColors(token: String): ResultWrapper<List<CarColorEntity>>
    suspend fun createCar(token: String, car: CarEntity): ResultWrapper<String>
    suspend fun updateCar(token: String, car: CarEntity): ResultWrapper<String>
    suspend fun setDefaultCar(token: String, id: String): ResultWrapper<String>
}