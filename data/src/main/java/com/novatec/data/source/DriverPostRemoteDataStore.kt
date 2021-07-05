package com.novatec.data.source

import com.novatec.data.model.DriverPostEntity
import com.novatec.data.repository.DriverPostDataStore
import com.novatec.data.repository.DriverPostRemote
import com.novatec.data.repository.PlaceDataStore
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class DriverPostRemoteDataStore @Inject constructor(private val driverPostRemote: DriverPostRemote) :
    DriverPostDataStore {

    override suspend fun createDriverPost(post: DriverPostEntity) =
        driverPostRemote.createDriverPost(post)

    override suspend fun deleteDriverPost(identifier: String) =
        driverPostRemote.deleteDriverPost(identifier)

    override suspend fun finishDriverPost(identifier: String) =
        driverPostRemote.finishDriverPost(identifier)

    override suspend fun getActiveDriverPosts() = driverPostRemote.getActiveDriverPosts()

    override suspend fun getHistoryDriverPosts(page: Int) =
        driverPostRemote.getHistoryDriverPosts(page)

    override suspend fun getDriverPostById(id: Long) = driverPostRemote.getDriverPostById(id)

    override suspend fun startTrip(id: Long) = driverPostRemote.startTrip(id)

    override suspend fun acceptOffer(id: Long) = driverPostRemote.acceptOffer(id)

    override suspend fun rejectOffer(id: Long) = driverPostRemote.rejectOffer(id)

    override suspend fun cancelMyOffer(id: Long) = driverPostRemote.cancelMyOffer(id)
    override suspend fun removePassengerFromPost(postId: Long,
                                                 passengerId: Long) =
        driverPostRemote.removePassengerFromPost(postId, passengerId)

    override suspend fun removeParcelFromPost(postId: Long,
                                              parcelId: Long) =
        driverPostRemote.removeParcelFromPost(postId, parcelId)


    override suspend fun getPassengerOffers(postId: Long) =
        driverPostRemote.getPassengerOffers(postId)

    override suspend fun getParcelOffers(postId: Long) = driverPostRemote.getParcelOffers(postId)

}