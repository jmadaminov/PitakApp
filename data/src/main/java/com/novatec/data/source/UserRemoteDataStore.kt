package com.novatec.data.source

import com.novatec.core.ResponseWrapper
import com.novatec.data.model.UserCredentialsEntity
import com.novatec.data.model.UserEntity
import com.novatec.data.repository.UserDataStore
import com.novatec.data.repository.UserRemote
import javax.inject.Inject

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class UserRemoteDataStore @Inject constructor(private val userRemote: UserRemote) :
    UserDataStore {

    override suspend fun userLogin(phoneNum: String) = userRemote.loginUser(phoneNum)

    override suspend fun userRegister(user: UserEntity) = userRemote.registerUser(user)

    override suspend fun confirmSms(userCredentialsEntity: UserCredentialsEntity) =
        userRemote.confirmUser(userCredentialsEntity)

    override suspend fun sendFeedback(feedback: String) =
        userRemote.sendFeedback(feedback)

    override suspend fun updateUserInfo(name: String,
                                        surName: String,
                                        uploadedAvatarId: Long?) =
        userRemote.updateUserInfo(name, surName,uploadedAvatarId)

    override suspend fun getActiveAppVersions()=
        userRemote.getActiveAppVersions()
//
//    override fun isCached(): Single<Boolean> {
//        throw UnsupportedOperationException()
//    }

}