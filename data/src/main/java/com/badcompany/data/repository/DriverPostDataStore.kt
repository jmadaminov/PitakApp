package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.DriverPostEntity
import com.badcompany.domain.domainmodel.DriverPost


interface DriverPostDataStore {
    suspend fun createDriverPost(token: String, post: DriverPostEntity): ResultWrapper<String>
    suspend fun deleteDriverPost(token: String, identifier: String): ResultWrapper<String>
    suspend fun finishDriverPost(token: String, identifier: String): ResultWrapper<String>
    suspend fun getActiveDriverPosts(token: String, lang: String): ResultWrapper<List<DriverPostEntity>>
    suspend fun getHistoryDriverPosts(token: String, lang: String,
                                      page: Int): ResultWrapper<List<DriverPostEntity>>

}