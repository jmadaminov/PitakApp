package com.novatec.domain.repository

import com.novatec.core.ResponseWrapper
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.AuthBody
import com.novatec.domain.domainmodel.Car
import com.novatec.domain.domainmodel.User
import com.novatec.domain.domainmodel.UserCredentials

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