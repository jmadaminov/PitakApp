package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.UserEntity


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface UserDataStore {

    suspend fun userLogin(phoneNum: String): ResultWrapper<String>
    suspend fun userRegister(user: UserEntity): ResultWrapper<String>

}