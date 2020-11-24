package com.badcompany.domain.repository

import com.badcompany.core.ResponseWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverOffer
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.domain.domainmodel.PassengerPost

interface PassengerPostRepository {

    suspend fun filterPassengerPost(filter: Filter): ResultWrapper<List<PassengerPost>>
    suspend fun offerARide(myOffer: DriverOffer): ResponseWrapper<Any>
//    suspend fun getPassengerPostById(id:Long): ResponseWrapper<PassengerPost>

}