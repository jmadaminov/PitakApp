package com.badcompany.domain.repository

import com.badcompany.core.ResponseWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.AuthBody
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.User
import com.badcompany.domain.domainmodel.UserCredentials

interface UserRepository {

    suspend fun loginUser(phoneNum: String): ResponseWrapper<UserCredentials?>
    suspend fun registerUser(user: User): ResultWrapper<String>
    suspend fun smsConfirm(userCredentials: UserCredentials): ResultWrapper<AuthBody>

    suspend fun sendFeedback(feedback: String): ResponseWrapper<Any?>

    suspend fun updateUserDetails(user: User): ResultWrapper<Unit>
    suspend fun addOrUpdateUserCar(car: Car): ResultWrapper<Unit>
    suspend fun getUserCars(userId: String): ResultWrapper<List<Car>>
    suspend fun deleteUserCar(carId: String): ResultWrapper<List<Car>>
    suspend fun getActiveAppVersions(): ResponseWrapper<List<String>>
    suspend fun updateUserInfo(name: String,
                               surName: String,
                               uploadedAvatarId: Long?): ResponseWrapper<Any?>

}