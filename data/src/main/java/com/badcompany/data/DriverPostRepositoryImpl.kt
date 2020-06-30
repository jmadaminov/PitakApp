package com.badcompany.data

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
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


    override suspend fun createDriverPost(token: String, post: DriverPost): ResultWrapper<String> {

        val response =
            factoryDriver.retrieveDataStore(false)
                .createDriverPost(token, driverPostMapper.mapToEntity(post))

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.value)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getActiveDriverPosts(token: String,
                                              lang: String): ResultWrapper<List<DriverPost>> {
        val response =
            factoryDriver.retrieveDataStore(false).getActiveDriverPosts(token, lang)

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {

                val posts = arrayListOf<DriverPost>()
                response.value.forEach { posts.add(driverPostMapper.mapFromEntity(it)) }
                ResultWrapper.Success(posts)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getHistoryDriverPosts(token: String,
                                               lang: String): ResultWrapper<List<DriverPost>> {
        val response =
            factoryDriver.retrieveDataStore(false).getHistoryDriverPosts(token, lang)

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val posts = arrayListOf<DriverPost>()
                response.value.forEach { posts.add(driverPostMapper.mapFromEntity(it)) }
                ResultWrapper.Success(posts)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


}