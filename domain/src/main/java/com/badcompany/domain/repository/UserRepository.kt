package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.User
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.core.ErrorWrapper
import com.badcompany.domain.domainmodel.AuthBody

interface UserRepository {

    suspend fun loginUser(phoneNum: String, deviceId: String): ResultWrapper<String>
    suspend fun registerUser(user: User): ResultWrapper<String>
    suspend fun smsConfirm(userCredentials: UserCredentials): ResultWrapper<AuthBody>

    fun updateUserDetails(user: User): ResultWrapper<Unit>
    fun addOrUpdateUserCar(car: Car): ResultWrapper< Unit>
    fun getUserCars(userId: String): ResultWrapper< List<Car>>
    fun deleteUserCar(carId: String): ResultWrapper< List<Car>>

}