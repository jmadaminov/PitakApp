package com.novatec.remote

import com.novatec.core.*
import com.novatec.data.model.DriverPostEntity
import com.novatec.data.model.OfferEntity
import com.novatec.data.repository.DriverPostRemote
import com.novatec.remote.ResponseFormatter.getFormattedResponse
import com.novatec.remote.ResponseFormatter.getFormattedResponseNullable
import com.novatec.remote.mapper.DriverPostMapper
import com.novatec.remote.mapper.OfferMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class DriverPostRemoteImpl @Inject constructor(
    private val authApi: AuthApi,
    private val postMapper: DriverPostMapper,
    private val offerMapper: OfferMapper,
) :
    DriverPostRemote {

    override suspend fun createDriverPost(post: DriverPostEntity): ResultWrapper<DriverPostEntity?> {
        val response =
            if (post.id != 0L) getFormattedResponseNullable {
                authApi.editPost(post.id, postMapper.mapFromEntity(post))
            }
            else getFormattedResponse {
                authApi.createPost(postMapper.mapFromEntity(post))
            }

        (return when (response) {
            is ResponseError -> {
                ErrorWrapper.RespError(message = response.message, code = response.code)
            }
            is ResponseSuccess -> {
                ResultWrapper.Success(if (response.value != null) postMapper.mapToEntity(response.value!!) else null)
            }
        }).exhaustive
    }


    override suspend fun deleteDriverPost(
        identifier: String): ResultWrapper<String> {
        return try {
            val response = authApi.deletePost(identifier)
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.RespError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun finishDriverPost(
        identifier: String): ResultWrapper<String> {
        return try {
            val response = authApi.finishPost(identifier)
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.RespError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getActiveDriverPosts(
    ): ResultWrapper<List<DriverPostEntity>> {

        return try {
            val response = authApi.getActivePosts()
            if (response.code == 1) {
                val posts = arrayListOf<DriverPostEntity>()
                response.data?.forEach { posts.add(postMapper.mapToEntity(it)) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.RespError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getHistoryDriverPosts(page: Int): ResultWrapper<List<DriverPostEntity>> {

        return try {
            val response = authApi.getHistoryPosts(page)
            if (response.code == 1) {
                val posts = arrayListOf<DriverPostEntity>()
                response.data?.data?.forEach { posts.add(postMapper.mapToEntity(it)) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.RespError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getDriverPostById(id: Long): ResponseWrapper<DriverPostEntity> {
        val response = getFormattedResponse { authApi.getDriverPostById(id) }
        return if (response is ResponseSuccess)
            ResponseSuccess(postMapper.mapToEntity(response.value))
        else response as ResponseError
    }

    override suspend fun startTrip(id: Long) =
        getFormattedResponseNullable { authApi.startTrip(id) }

    override suspend fun acceptOffer(id: Long) =
        getFormattedResponse { authApi.acceptOffer(id) }

    override suspend fun rejectOffer(id: Long) =
        getFormattedResponse { authApi.rejectOffer(id) }

    override suspend fun cancelMyOffer(id: Long) =
        getFormattedResponse { authApi.cancelMyOffer(id) }

    override suspend fun removePassengerFromPost(postId: Long,
                                                 commuterId: Long): ResponseWrapper<DriverPostEntity?> {
        val response = getFormattedResponseNullable {
            authApi.removePassengerFromPost(postId,
                                            commuterId)
        }
        return when (response) {
            is ResponseError -> {
                response
            }
            is ResponseSuccess -> {
                ResponseSuccess(if (response.value != null) postMapper.mapToEntity(response.value!!) else null)
            }
        }

    }

    override suspend fun removeParcelFromPost(postId: Long,
                                              parcelId: Long): ResponseWrapper<DriverPostEntity?> {
        val response = getFormattedResponseNullable {
            authApi.removeParcelFromPost(postId,
                                         parcelId)
        }
        return when (response) {
            is ResponseError -> {
                response
            }
            is ResponseSuccess -> {
                ResponseSuccess(if (response.value != null) postMapper.mapToEntity(response.value!!) else null)
            }
        }

    }

    override suspend fun getPassengerOffers(postId: Long): ResultWrapper<List<OfferEntity>> {
        return try {
            val response = authApi.getOffersForPost(postId)
            if (response.code == 1) {
                ResultWrapper.Success(response.data!!.map { offerMapper.mapToEntity(it) })
            } else ErrorWrapper.RespError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getParcelOffers(postId: Long): ResultWrapper<List<OfferEntity>> {
        return try {
            val response = authApi.getParcelOffersForPost(postId)
            if (response.code == 1) {
                ResultWrapper.Success(response.data!!.map { offerMapper.mapToEntity(it) })
            } else ErrorWrapper.RespError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


}