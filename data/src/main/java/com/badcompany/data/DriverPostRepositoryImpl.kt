package com.badcompany.data

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.mapper.DriverPostMapper
import com.badcompany.data.source.DriverPostDataStoreFactory
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.repository.PlaceRepository
import com.badcompany.domain.repository.DriverPostRepository
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
            factoryDriver.retrieveDataStore(false).createDriverPost(token, driverPostMapper.mapToEntity(post))

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.value)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


}