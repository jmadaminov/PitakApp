package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.*


interface CarRepository {

    suspend fun getCars(): ResultWrapper<List<CarDetails>>
    suspend fun getCarModels(): ResultWrapper<List<CarModelBody>>
    suspend fun getCarColors(): ResultWrapper<List<CarColorBody>>
    suspend fun createCar( car: Car): ResultWrapper<Car>
    suspend fun updateCar( car: Car): ResultWrapper<Car>
    suspend fun deleteCar( id: Long): ResultWrapper<List<CarDetails>>
    suspend fun setDefaultCar( id:Long): ResultWrapper<String>


}