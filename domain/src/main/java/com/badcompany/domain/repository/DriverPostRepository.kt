package com.badcompany.domain.repository

import com.badcompany.core.ResponseWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.domainmodel.PassengerPost

interface DriverPostRepository {

    suspend fun createDriverPost(post: DriverPost): ResultWrapper<DriverPost>
    suspend fun deleteDriverPost(identifier: String): ResultWrapper<String>
    suspend fun finishDriverPost(identifier: String): ResultWrapper<String>
    suspend fun getActiveDriverPosts(): ResultWrapper<List<DriverPost>>
    suspend fun getHistoryDriverPosts(page: Int): ResultWrapper<List<DriverPost>>


    suspend fun getDriverPostById(id: Long): ResponseWrapper<DriverPost>

    suspend fun acceptOffer(id: Long): ResponseWrapper<String?>
    suspend fun rejectOffer(id: Long): ResponseWrapper<String?>
    suspend fun cancelMyOffer(id: Long): ResponseWrapper<String?>
}