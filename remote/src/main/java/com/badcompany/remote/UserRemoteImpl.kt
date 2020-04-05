package com.badcompany.remote

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.data.repository.UserRemote
import com.badcompany.remote.mapper.UserCredentialsMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserRemoteImpl @Inject constructor(private val bufferooService: BufferooService,
                                         private val userCredMapper: UserCredentialsMapper) :
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

    override fun loginUser(userCredentials: UserCredentialsEntity): ResultWrapper<Exception, String> {
        return bufferooService.userLogin(userCredMapper.mapFromEntity(userCredentials))

    }

}