package com.badcompany.remote

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.data.repository.UserRemote
import com.badcompany.remote.mapper.UserCredentialsMapper
import com.badcompany.remote.mapper.UserMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserRemoteImpl @Inject constructor(private val apiService: ApiService,
                                         private val userCredMapper: UserCredentialsMapper,
                                         private val userMapper: UserMapper) :
    UserRemote {

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

    override suspend fun loginUser(userCredentials: UserCredentialsEntity): ResultWrapper<String> {


        return apiService.userLogin(userCredMapper.mapFromEntity(userCredentials))
    }

    override suspend fun registerUser(user: UserEntity): ResultWrapper<String> {
        return try {
            val response = apiService.userRegister(userMapper.mapFromEntity(user))
            if (response.code == 0) ResultWrapper.Success(response.data!!.password!!)
            else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

}