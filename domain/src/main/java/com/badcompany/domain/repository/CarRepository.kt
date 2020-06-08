package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.domainmodel.PhotoBody
import java.io.File

interface CarRepository {

    suspend fun getCarModels(token: String): ResultWrapper<List<CarModelBody>>
    suspend fun getCarColors(token: String): ResultWrapper<List<CarColorBody>>
    suspend fun createCar(token: String, car: Car): ResultWrapper<String>
    suspend fun updateCar(token: String, car: Car): ResultWrapper<String>
    suspend fun setDefaultCar(token: String, id:String): ResultWrapper<String>


}