package com.novatec.domain.repository

import com.novatec.core.ResponseWrapper
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.domainmodel.PassengerPost

interface DriverPostRepository {

    suspend fun createDriverPost(post: DriverPost): ResultWrapper<DriverPost?>
    suspend fun deleteDriverPost(identifier: String): ResultWrapper<String>
    suspend fun finishDriverPost(identifier: String): ResultWrapper<String>
    suspend fun getActiveDriverPosts(): ResultWrapper<List<DriverPost>>
    suspend fun getHistoryDriverPosts(page: Int): ResultWrapper<List<DriverPost>>


    suspend fun getDriverPostById(id: Long): ResponseWrapper<DriverPost>
    suspend fun startTrip(id: Long): ResponseWrapper<String?>

    suspend fun acceptOffer(id: Long): ResponseWrapper<String?>
    suspend fun rejectOffer(id: Long): ResponseWrapper<String?>
    suspend fun cancelMyOffer(id: Long): ResponseWrapper<String?>
}