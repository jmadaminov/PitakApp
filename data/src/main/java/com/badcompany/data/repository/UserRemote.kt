package com.badcompany.data.repository

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResponseWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.AuthEntity
import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.domain.domainmodel.AuthBody
import com.badcompany.domain.domainmodel.UserCredentials


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
    suspend  fun confirmUser(user: UserCredentialsEntity): ResultWrapper<AuthEntity>
    suspend fun updateUserInfo(name: String, surName: String, uploadedAvatarId: Long?): ResponseWrapper<Any?>
}