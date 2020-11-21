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

    override suspend fun createDriverPost(
                                          post: DriverPostEntity): ResultWrapper<String> {
        return driverPostRemote.createDriverPost( post)
    }

    override suspend fun deleteDriverPost(
                                          identifier: String): ResultWrapper<String> {
        return driverPostRemote.deleteDriverPost( identifier)

    }

    override suspend fun finishDriverPost(
                                          identifier: String): ResultWrapper<String> {
        return driverPostRemote.finishDriverPost( identifier)

    }

    override suspend fun getActiveDriverPosts(
                                              ): ResultWrapper<List<DriverPostEntity>> {

        return driverPostRemote.getActiveDriverPosts( )

    }

    override suspend fun getHistoryDriverPosts(                                                                                              page: Int): ResultWrapper<List<DriverPostEntity>> {
        return driverPostRemote.getHistoryDriverPosts( page)
    }


}