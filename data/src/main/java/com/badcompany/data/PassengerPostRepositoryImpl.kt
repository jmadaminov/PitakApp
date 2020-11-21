package com.badcompany.data

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.mapper.FilterMapper
import com.badcompany.data.mapper.PassengerPostMapper
import com.badcompany.data.source.PassengerPostDataStoreFactory
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
                                                      private val filterMapper: FilterMapper) :
    PassengerPostRepository {

    override suspend fun filterPassengerPost(filter: Filter): ResultWrapper<List<PassengerPost>> {

        val response = factoryPassenger.retrieveDataStore(false)
            .filterPassengerPost(  filterMapper.mapToEntity(filter))

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val posts = arrayListOf<PassengerPost>()
                response.value.forEach { posts.add(passengerPostMapper.mapFromEntity(it)) }
                ResultWrapper.Success(posts)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


//    override suspend fun getHistoryPassengerPosts(
//                                               ,
//                                               page: Int): ResultWrapper<List<PassengerPost>> {
//        val response =
//            factoryPassenger.retrieveDataStore(false).getHistoryPassengerPosts( page)
//
//        return when (response) {
//            is ErrorWrapper.ResponseError -> response
//            is ErrorWrapper.SystemError -> response
//            is ResultWrapper.Success -> {
//                val posts = arrayListOf<PassengerPost>()
//                response.value.forEach { posts.add(driverPostMapper.mapFromEntity(it)) }
//                ResultWrapper.Success(posts)
//            }
//            ResultWrapper.InProgress -> ResultWrapper.InProgress
//        }
//    }


}