package com.badcompany.remote

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.AuthEntity
import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.data.repository.UserRemote
import com.badcompany.remote.mapper.AuthMapper
import com.badcompany.remote.mapper.UserCredentialsMapper
import com.badcompany.remote.mapper.UserMapper
import com.badcompany.remote.model.LoginRequest
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserRemoteImpl @Inject constructor(private val apiService: ApiService,
                                         private val userCredMapper: UserCredentialsMapper,
                                         private val userMapper: UserMapper,
                                         private val authMapper: AuthMapper) : UserRemote {

//    /**
//     * Retrieve a list of [BufferooEntity] instances from the [BufferooService].
//     */
//    override fun getBufferoos(): Flowable<List<UserEntity>> {
//        return bufferooService.getBufferoos()
//                .map { it.team }
//                .map {
//                    val entities = mutableListOf<UserEntity>()
//                    it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
//                    entities
//                }
//    }

    override suspend fun loginUser(phoneNum: String): ResultWrapper<String> {
        return try {
            val response = apiService.userLogin(LoginRequest(phoneNum))
            if (response.code == 1) ResultWrapper.Success(response.data!!.password!!)
            else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun registerUser(user: UserEntity): ResultWrapper<String> {
        return try {
            val response = apiService.userRegister(userMapper.mapFromEntity(user))
            if (response.code == 1) ResultWrapper.Success(response.data!!.password!!)
            else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun confirmUser(user: UserCredentialsEntity): ResultWrapper<AuthEntity> {
        return try {
            val response = apiService.smsConfirm(userCredMapper.mapFromEntity(user))
            if (response.code == 1) ResultWrapper.Success(authMapper.mapToEntity(response.data!!))
            else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

}