package com.badcompany.data.repository

import com.badcompany.core.ResponseWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.DriverPostEntity
import com.badcompany.domain.domainmodel.DriverPost


interface DriverPostDataStore {
    suspend fun createDriverPost(post: DriverPostEntity): ResultWrapper<DriverPostEntity>
    suspend fun deleteDriverPost(identifier: String): ResultWrapper<String>
    suspend fun finishDriverPost(identifier: String): ResultWrapper<String>
    suspend fun getActiveDriverPosts(): ResultWrapper<List<DriverPostEntity>>
    suspend fun getHistoryDriverPosts(page: Int): ResultWrapper<List<DriverPostEntity>>

    suspend fun getDriverPostById(id: Long): ResponseWrapper<DriverPostEntity>
    suspend fun startTrip(id: Long): ResponseWrapper<String?>
    suspend fun acceptOffer(id: Long): ResponseWrapper<String?>
    suspend fun rejectOffer(id: Long): ResponseWrapper<String?>
    suspend fun cancelMyOffer(id: Long): ResponseWrapper<String?>

}