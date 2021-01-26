package com.badcompany.data.repository

import com.badcompany.core.ResponseWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.AuthEntity
import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.domain.domainmodel.UserCredentials


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface UserDataStore {

    suspend fun userLogin(phoneNum: String): ResponseWrapper<UserCredentialsEntity?>
    suspend fun userRegister(user: UserEntity): ResultWrapper<String>
    suspend fun confirmSms(userCredentialsEntity: UserCredentialsEntity): ResultWrapper<AuthEntity>
    suspend fun sendFeedback(feedback:String): ResponseWrapper<Any?>
    suspend fun updateUserInfo(name: String, surName: String, uploadedAvatarId: Long?): ResponseWrapper<Any?>
}