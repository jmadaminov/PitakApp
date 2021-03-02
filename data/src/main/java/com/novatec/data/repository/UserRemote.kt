package com.novatec.data.repository

import com.novatec.core.ErrorWrapper
import com.novatec.core.ResponseWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.model.AuthEntity
import com.novatec.data.model.UserCredentialsEntity
import com.novatec.data.model.UserEntity
import com.novatec.domain.domainmodel.AuthBody
import com.novatec.domain.domainmodel.UserCredentials


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface UserRemote {

    /**
     * Retrieve a list of Bufferoos, from the cache
     */
   suspend fun loginUser(phoneNum: String): ResponseWrapper<UserCredentialsEntity?>

    suspend  fun registerUser(user: UserEntity): ResultWrapper<String>
    suspend fun sendFeedback(feedback:String): ResponseWrapper<Any?>
    suspend  fun confirmUser(user: UserCredentialsEntity): ResultWrapper<AuthEntity>
    suspend fun updateUserInfo(name: String, surName: String, uploadedAvatarId: Long?): ResponseWrapper<Any?>
    suspend fun getActiveAppVersions(): ResponseWrapper<List<String>>
}