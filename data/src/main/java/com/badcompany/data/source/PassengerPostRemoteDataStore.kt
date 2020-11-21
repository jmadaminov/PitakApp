package com.badcompany.data.source

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.FilterEntity
import com.badcompany.data.model.PassengerPostEntity
import com.badcompany.data.repository.PassengerPostDataStore
import com.badcompany.data.repository.PassengerPostRemote
import com.badcompany.data.repository.PlaceDataStore
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PassengerPostRemoteDataStore @Inject constructor(private val passengerPostRemote: PassengerPostRemote) :
    PassengerPostDataStore {

    override suspend fun filterPassengerPost(        filter: FilterEntity): ResultWrapper<List<PassengerPostEntity>> {

        return passengerPostRemote.filterPassengerPost(  filter)
    }

}