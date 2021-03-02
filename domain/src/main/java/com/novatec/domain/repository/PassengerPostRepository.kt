package com.novatec.domain.repository

import com.novatec.core.ResponseWrapper
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverOffer
import com.novatec.domain.domainmodel.Filter
import com.novatec.domain.domainmodel.PassengerPost

interface PassengerPostRepository {

    suspend fun filterPassengerPost(filter: Filter): ResultWrapper<List<PassengerPost>>
    suspend fun offerARide(myOffer: DriverOffer): ResponseWrapper<Any>
    suspend fun getPassengerPostById(id:Long): ResponseWrapper<PassengerPost>

}