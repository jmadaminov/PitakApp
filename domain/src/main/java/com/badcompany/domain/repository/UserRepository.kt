package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.User
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.core.ErrorWrapper

interface UserRepository {

    suspend fun loginUser(userCredentials: UserCredentials): ResultWrapper<ErrorWrapper, String>
    suspend fun registerUser(user: User): ResultWrapper<ErrorWrapper, String>

    fun updateUserDetails(user: User): ResultWrapper<Exception, Unit>
    fun addOrUpdateUserCar(car: Car): ResultWrapper<Exception, Unit>
    fun getUserCars(userId: String): ResultWrapper<Exception, List<Car>>
    fun deleteUserCar(carId: String): ResultWrapper<Exception, List<Car>>

}