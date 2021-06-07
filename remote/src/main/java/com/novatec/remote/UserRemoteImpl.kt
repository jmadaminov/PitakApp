package com.novatec.remote

import com.novatec.core.*
import com.novatec.data.model.AuthEntity
import com.novatec.data.model.UserCredentialsEntity
import com.novatec.data.model.UserEntity
import com.novatec.data.repository.UserRemote
import com.novatec.remote.ResponseFormatter.getFormattedResponse
import com.novatec.remote.ResponseFormatter.getFormattedResponseNullable
import com.novatec.remote.mapper.AuthMapper
import com.novatec.remote.mapper.UserCredentialsMapper
import com.novatec.remote.mapper.UserMapper
import com.novatec.remote.model.FeedbackBody
import com.novatec.remote.model.IdNameBody
import com.novatec.remote.model.LoginRequest
import com.novatec.remote.model.ReqUpdateProfileInfo
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserRemoteImpl @Inject constructor(private val apiService: ApiService,
                                         private val authApi: AuthApi,
                                         private val userCredMapper: UserCredentialsMapper,
                                         private val userMapper: UserMapper,
                                         private val authMapper: AuthMapper) : UserRemote {

    override suspend fun loginUser(phoneNum: String): ResponseWrapper<UserCredentialsEntity?> {
        return try {
            val response =
                getFormattedResponseNullable { apiService.userLogin(LoginRequest(phoneNum)) }
            if (response is ResponseSuccess) return ResponseSuccess(null)
            else return response as ResponseError
        } catch (e: Exception) {
            ResponseError(e.localizedMessage)
        }
    }

    override suspend fun registerUser(user: UserEntity): ResultWrapper<String> {
        return try {
            val response = apiService.userRegister(userMapper.mapFromEntity(user))
            if (response.code == 1) ResultWrapper.Success("")
            else ErrorWrapper.RespError(response.code, response.message?:"")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun sendFeedback(feedback: String) =
        getFormattedResponseNullable { authApi.sendFeedback(FeedbackBody(feedback)) }

    override suspend fun confirmUser(user: UserCredentialsEntity): ResultWrapper<AuthEntity> {
        return try {
            val response = apiService.smsConfirm(userCredMapper.mapFromEntity(user))
            if (response.code == 1) ResultWrapper.Success(authMapper.mapToEntity(response.data!!))
            else ErrorWrapper.RespError(response.code, response.message?:"")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun updateUserInfo(name: String, surName: String, uploadedAvatarId: Long?) =
        getFormattedResponseNullable {
            authApi.updateUserInfo(ReqUpdateProfileInfo(name,
                                                        surName,
                                                        uploadedAvatarId?.let {
                                                                   IdNameBody(uploadedAvatarId)
                                                               }))
        }

    override suspend fun getActiveAppVersions(): ResponseWrapper<List<String>> {
        val response = getFormattedResponse { apiService.getActiveAppVersions() }
        return when (response) {
            is ResponseError -> response
            is ResponseSuccess -> {
                val versions = arrayListOf<String>()
                response.value.forEach {
                    versions.add(it.version)
                }
                ResponseSuccess(versions)
            }
        }.exhaustive
    }

}