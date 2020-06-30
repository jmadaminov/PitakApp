package com.badcompany.data.source

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.DriverPostEntity
import com.badcompany.data.repository.DriverPostDataStore
import com.badcompany.data.repository.DriverPostRemote
import com.badcompany.data.repository.PlaceDataStore
import com.badcompany.domain.domainmodel.DriverPost
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class DriverPostRemoteDataStore @Inject constructor(private val driverPostRemote: DriverPostRemote) :
    DriverPostDataStore {

    override suspend fun createDriverPost(token: String,
                                          post: DriverPostEntity): ResultWrapper<String> {
        return driverPostRemote.createDriverPost(token, post)
    }

    override suspend fun getActiveDriverPosts(token: String,
                                              lang: String): ResultWrapper<List<DriverPostEntity>> {

        return driverPostRemote.getActiveDriverPosts(token, lang)

    }

    override suspend fun getHistoryDriverPosts(token: String,
                                               lang: String): ResultWrapper<List<DriverPostEntity>> {
        return driverPostRemote.getHistoryDriverPosts(token, lang)
    }


}