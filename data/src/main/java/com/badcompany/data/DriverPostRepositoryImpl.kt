package com.badcompany.data

import com.badcompany.core.*
import com.badcompany.data.mapper.DriverPostMapper
import com.badcompany.data.source.DriverPostDataStoreFactory
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.repository.DriverPostRepository
import com.badcompany.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class DriverPostRepositoryImpl @Inject constructor(private val factoryDriver: DriverPostDataStoreFactory,
                                                   private val driverPostMapper: DriverPostMapper) :
    DriverPostRepository {


    override suspend fun createDriverPost(post: DriverPost): ResultWrapper<DriverPost> {
        val response = factoryDriver.retrieveDataStore(false)
            .createDriverPost(driverPostMapper.mapToEntity(post))

        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(driverPostMapper.mapFromEntity(response.value))
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
                val posts = arrayListOf<DriverPost>()
                response.value.forEach { posts.add(driverPostMapper.mapFromEntity(it)) }
                ResultWrapper.Success(posts)
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

    override suspend fun acceptOffer(id: Long) =
        factoryDriver.retrieveDataStore(false).acceptOffer(id)

    override suspend fun rejectOffer(id: Long) =
        factoryDriver.retrieveDataStore(false).rejectOffer(id)

    override suspend fun cancelMyOffer(id: Long) =
        factoryDriver.retrieveDataStore(false).cancelMyOffer(id)


}