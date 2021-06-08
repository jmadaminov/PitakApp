package com.novatec.data

import com.novatec.core.*
import com.novatec.data.mapper.DriverPostMapper
import com.novatec.data.mapper.OfferMapper
import com.novatec.data.source.DriverPostDataStoreFactory
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.domainmodel.Offer
import com.novatec.domain.repository.DriverPostRepository
import com.novatec.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class DriverPostRepositoryImpl @Inject constructor(private val factoryDriver: DriverPostDataStoreFactory,
                                                   private val driverPostMapper: DriverPostMapper,
                                                   private val offerMapper: OfferMapper) :
    DriverPostRepository {


    override suspend fun createDriverPost(post: DriverPost): ResultWrapper<DriverPost?> {
        val response = factoryDriver.retrieveDataStore(false)
            .createDriverPost(driverPostMapper.mapToEntity(post))

        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(if (response.value != null) driverPostMapper.mapFromEntity(
                    response.value!!) else null)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun deleteDriverPost(
        identifier: String): ResultWrapper<String> {

        return factoryDriver.retrieveDataStore(false).deleteDriverPost(identifier)
    }

    override suspend fun finishDriverPost(
        identifier: String): ResultWrapper<String> {
        return factoryDriver.retrieveDataStore(false).finishDriverPost(identifier)

    }

    override suspend fun getActiveDriverPosts(
    ): ResultWrapper<List<DriverPost>> {
        val response =
            factoryDriver.retrieveDataStore(false).getActiveDriverPosts()

        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {

                val posts = arrayListOf<DriverPost>()
                response.value.forEach { posts.add(driverPostMapper.mapFromEntity(it)) }
                ResultWrapper.Success(posts)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getHistoryDriverPosts(page: Int): ResultWrapper<List<DriverPost>> {
        val response =
            factoryDriver.retrieveDataStore(false).getHistoryDriverPosts(page)

        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.value.map { driverPostMapper.mapFromEntity(it) })
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getPassengerOffers(postId: Long): ResultWrapper<List<Offer>> {
        val response =
            factoryDriver.retrieveDataStore(false).getPassengerOffers(postId)

        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.value.map { offerMapper.mapFromEntity(it) })
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }

    }

    override suspend fun getParcelOffers(postId: Long): ResultWrapper<List<Offer>> {
        val response =
            factoryDriver.retrieveDataStore(false).getParcelOffers(postId)

        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.value.map { offerMapper.mapFromEntity(it) })
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getDriverPostById(id: Long): ResponseWrapper<DriverPost> {
        val response = factoryDriver.retrieveDataStore(false).getDriverPostById(id)
        return if (response is ResponseSuccess)
            ResponseSuccess(driverPostMapper.mapFromEntity(response.value))
        else response as ResponseError
    }

    override suspend fun startTrip(id: Long) =
        factoryDriver.retrieveDataStore(false).startTrip(id)

    override suspend fun acceptOffer(id: Long) =
        factoryDriver.retrieveDataStore(false).acceptOffer(id)

    override suspend fun rejectOffer(id: Long) =
        factoryDriver.retrieveDataStore(false).rejectOffer(id)

    override suspend fun cancelMyOffer(id: Long) =
        factoryDriver.retrieveDataStore(false).cancelMyOffer(id)

    override suspend fun removePassengerFromPost(postId: Long,
                                                 passengerId: Long): ResponseWrapper<DriverPost?> {

        val resp =
            factoryDriver.retrieveDataStore(false).removePassengerFromPost(postId, passengerId)

        return when (resp) {
            is ResponseError -> {
                resp
            }
            is ResponseSuccess -> {
                ResponseSuccess(if (resp.value != null) driverPostMapper.mapFromEntity(resp.value!!) else null)
            }
        }

    }


}