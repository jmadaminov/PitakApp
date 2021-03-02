package com.novatec.data.source

import com.novatec.data.model.DriverOfferEntity
import com.novatec.data.model.FilterEntity
import com.novatec.data.repository.PassengerPostDataStore
import com.novatec.data.repository.PassengerPostRemote
import com.novatec.data.repository.PlaceDataStore
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PassengerPostRemoteDataStore @Inject constructor(private val passengerPostRemote: PassengerPostRemote) :
    PassengerPostDataStore {

    override suspend fun filterPassengerPost(filter: FilterEntity) =
        passengerPostRemote.filterPassengerPost(filter)

    override suspend fun offerARide(myOffer: DriverOfferEntity) =
        passengerPostRemote.offerARide(myOffer)

    override suspend fun getPassengerPostById(id: Long) =
        passengerPostRemote.getPassengerPostById(id)


}