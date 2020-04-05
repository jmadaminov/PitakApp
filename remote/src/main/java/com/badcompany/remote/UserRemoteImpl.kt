package com.badcompany.remote

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.UserEntity
import com.badcompany.data.repository.UserRemote
import io.reactivex.Flowable
import org.buffer.android.boilerplate.remote.BufferooService
import com.badcompany.remote.mapper.UserEntityMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserRemoteImpl @Inject constructor(private val bufferooService: BufferooService,
                                         private val entityMapper: UserEntityMapper):
        UserRemote {

    /**
     * Retrieve a list of [BufferooEntity] instances from the [BufferooService].
     */
    override fun getBufferoos(): Flowable<List<UserEntity>> {
        return bufferooService.getBufferoos()
                .map { it.team }
                .map {
                    val entities = mutableListOf<UserEntity>()
                    it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                    entities
                }
    }

    override fun loginUser(): ResultWrapper<Exception, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}