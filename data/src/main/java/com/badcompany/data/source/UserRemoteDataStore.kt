package com.badcompany.data.source

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.data.repository.UserDataStore
import com.badcompany.data.repository.UserRemote
import javax.inject.Inject

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class UserRemoteDataStore @Inject constructor(private val userRemote: UserRemote) :
    UserDataStore {

//    override fun clearBufferoos(): Completable {
//        throw UnsupportedOperationException()
//    }
//
//    override fun saveBufferoos(bufferoos: List<BufferooEntity>): Completable {
//        throw UnsupportedOperationException()
//    }
//
//    /**
//     * Retrieve a list of [BufferooEntity] instances from the API
//     */
    override fun userLogin(credentials: UserCredentialsEntity): ResultWrapper<Exception, String> {
        return userRemote.loginUser(credentials)
    }
//
//    override fun isCached(): Single<Boolean> {
//        throw UnsupportedOperationException()
//    }

}