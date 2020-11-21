package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.DriverPostEntity


interface DriverPostDataStore {
    suspend fun createDriverPost(post: DriverPostEntity): ResultWrapper<String>
    suspend fun deleteDriverPost(identifier: String): ResultWrapper<String>
    suspend fun finishDriverPost(identifier: String): ResultWrapper<String>
    suspend fun getActiveDriverPosts(): ResultWrapper<List<DriverPostEntity>>
    suspend fun getHistoryDriverPosts(page: Int): ResultWrapper<List<DriverPostEntity>>

}