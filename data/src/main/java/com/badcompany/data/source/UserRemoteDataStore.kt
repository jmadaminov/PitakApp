package com.badcompany.data.source

import com.badcompany.core.ResponseWrapper
import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.data.repository.UserDataStore
import com.badcompany.data.repository.UserRemote
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

    override suspend fun updateUserInfo(name: String,
                                        surName: String,
                                        uploadedAvatarId: Long?) =
        userRemote.updateUserInfo(name, surName,uploadedAvatarId)
//
//    override fun isCached(): Single<Boolean> {
//        throw UnsupportedOperationException()
//    }

}