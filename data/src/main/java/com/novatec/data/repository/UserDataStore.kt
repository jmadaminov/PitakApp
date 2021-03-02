package com.novatec.data.repository

import com.novatec.core.ResponseWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.model.AuthEntity
import com.novatec.data.model.UserCredentialsEntity
import com.novatec.data.model.UserEntity


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface UserDataStore {

    suspend fun userLogin(phoneNum: String): ResponseWrapper<UserCredentialsEntity?>
    suspend fun userRegister(user: UserEntity): ResultWrapper<String>
    suspend fun confirmSms(userCredentialsEntity: UserCredentialsEntity): ResultWrapper<AuthEntity>
    suspend fun sendFeedback(feedback: String): ResponseWrapper<Any?>
    suspend fun updateUserInfo(name: String,
                               surName: String,
                               uploadedAvatarId: Long?): ResponseWrapper<Any?>

    suspend fun getActiveAppVersions(): ResponseWrapper<List<String>>
}