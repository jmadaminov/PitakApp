package com.badcompany.data

import com.badcompany.core.*
import com.badcompany.data.mapper.DriverOfferMapper
import com.badcompany.data.mapper.FilterMapper
import com.badcompany.data.mapper.PassengerPostMapper
import com.badcompany.data.source.PassengerPostDataStoreFactory
import com.badcompany.domain.domainmodel.DriverOffer
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.domain.repository.PassengerPostRepository
import com.badcompany.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class PassengerPostRepositoryImpl @Inject constructor(private val factoryPassenger: PassengerPostDataStoreFactory,
                                                      private val passengerPostMapper: PassengerPostMapper,
                                                      private val filterMapper: FilterMapper,
                                                      private val driverOfferMapper: DriverOfferMapper) :
    PassengerPostRepository {

    override suspend fun filterPassengerPost(filter: Filter): ResultWrapper<List<PassengerPost>> {

        val response = factoryPassenger.retrieveDataStore(false)
            .filterPassengerPost(filterMapper.mapToEntity(filter))

        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val posts = arrayListOf<PassengerPost>()
                response.value.forEach { posts.add(passengerPostMapper.mapFromEntity(it)) }
                ResultWrapper.Success(posts)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun offerARide(myOffer: DriverOffer): ResponseWrapper<Any> {
        return factoryPassenger.retrieveDataStore(false)
            .offerARide(driverOfferMapper.mapToEntity(myOffer))
    }

    override suspend fun getPassengerPostById(id: Long): ResponseWrapper<PassengerPost> {
        val response = factoryPassenger.retrieveDataStore(false).getPassengerPostById(id)
        return when (response) {
            is ResponseError -> response
            is ResponseSuccess -> ResponseSuccess(passengerPostMapper.mapFromEntity(response.value))
        }.exhaustive
    }


}


