package com.novatec.domain.repository

import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.*


interface CarRepository {

    suspend fun getCars(): ResultWrapper<List<CarDetails>>
    suspend fun getCarModels(): ResultWrapper<List<CarModelBody>>
    suspend fun getCarColors(): ResultWrapper<List<CarColorBody>>
    suspend fun createCar( car: Car): ResultWrapper<Car>
    suspend fun updateCar( car: Car): ResultWrapper<Car>
    suspend fun deleteCar( id: Long): ResultWrapper<List<CarDetails>>
    suspend fun setDefaultCar( id:Long): ResultWrapper<String>


}