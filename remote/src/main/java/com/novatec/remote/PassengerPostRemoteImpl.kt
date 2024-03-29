package com.novatec.remote

import com.novatec.core.*
import com.novatec.data.model.DriverOfferEntity
import com.novatec.data.model.FilterEntity
import com.novatec.data.model.PassengerPostEntity
import com.novatec.data.repository.PassengerPostRemote
import com.novatec.remote.mapper.DriverOfferMapper
import com.novatec.remote.mapper.FilterMapper
import com.novatec.remote.mapper.PassengerPostMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PassengerPostRemoteImpl @Inject constructor(
    private val authApi: AuthApi,
    private val postMapper: PassengerPostMapper,
    private val filterMapper: FilterMapper,
    private val offerMapper: DriverOfferMapper) :
    PassengerPostRemote {

    override suspend fun filterPassengerPost(filter: FilterEntity): ResultWrapper<List<PassengerPostEntity>> {
        return try {
            val response =
                authApi.filterPassengerPost(filterMapper.mapFromEntity(filter))
            if (response.code == 1) {
                val posts = arrayListOf<PassengerPostEntity>()
                response.data?.data?.forEach { posts.add(postMapper.mapToEntity(it)) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.RespError(response.code, response.message?:"")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun offerARide(myOffer: DriverOfferEntity) =
        ResponseFormatter.getFormattedResponse {
            authApi.offerARide(offerMapper.mapFromEntity(myOffer))
        }

    override suspend fun getPassengerPostById(id: Long): ResponseWrapper<PassengerPostEntity> {
        val response =
            ResponseFormatter.getFormattedResponse { authApi.getPassengerPostById(id) }

        return when (response) {
            is ResponseError -> response
            is ResponseSuccess -> ResponseSuccess(postMapper.mapToEntity(response.value))
        }.exhaustive
    }

}