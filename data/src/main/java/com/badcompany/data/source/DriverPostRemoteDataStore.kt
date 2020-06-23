package com.badcompany.data.source

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.DriverPostEntity
import com.badcompany.data.repository.PlaceDataStore
import com.badcompany.data.repository.PostDataStore
import com.badcompany.data.repository.DriverPostRemote
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class DriverPostRemoteDataStore @Inject constructor(private val driverPostRemote: DriverPostRemote) :
    PostDataStore {
    override suspend fun createDriverPost(token: String,
                                          post: DriverPostEntity): ResultWrapper<String> {
        return driverPostRemote.createDriverPost(token, post)
    }


}