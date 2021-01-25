package com.badcompany.remote

import com.badcompany.core.*
import com.badcompany.data.model.DriverOfferEntity
import com.badcompany.data.model.FilterEntity
import com.badcompany.data.model.PassengerPostEntity
import com.badcompany.data.repository.PassengerPostRemote
import com.badcompany.remote.mapper.DriverOfferMapper
import com.badcompany.remote.mapper.FilterMapper
import com.badcompany.remote.mapper.PassengerPostMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PassengerPostRemoteImpl @Inject constructor(
    private val authorizedApiService: AuthorizedApiService,
    private val postMapper: PassengerPostMapper,
    private val filterMapper: FilterMapper,
    private val offerMapper: DriverOfferMapper) :
    PassengerPostRemote {

    override suspend fun filterPassengerPost(filter: FilterEntity): ResultWrapper<List<PassengerPostEntity>> {
        return try {
            val response =
                authorizedApiService.filterPassengerPost(filterMapper.mapFromEntity(filter))
            if (response.code == 1) {
                val posts = arrayListOf<PassengerPostEntity>()
                response.data?.data?.forEach { posts.add(postMapper.mapToEntity(it)) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.RespError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun offerARide(myOffer: DriverOfferEntity) =
        ResponseFormatter.getFormattedResponse {
            authorizedApiService.offerARide(offerMapper.mapFromEntity(myOffer))
        }

    override suspend fun getPassengerPostById(id: Long): ResponseWrapper<PassengerPostEntity> {
        val response =
            ResponseFormatter.getFormattedResponse { authorizedApiService.getPassengerPostById(id) }

        return when (response) {
            is ResponseError -> response
            is ResponseSuccess -> ResponseSuccess(postMapper.mapToEntity(response.value))
        }.exhaustive
    }

}