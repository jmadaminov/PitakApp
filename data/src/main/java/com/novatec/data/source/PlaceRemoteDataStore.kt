package com.novatec.data.source

import com.novatec.core.ResultWrapper
import com.novatec.data.model.PlaceEntity
import com.novatec.data.repository.PlaceDataStore
import com.novatec.data.repository.PlaceRemote
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PlaceRemoteDataStore @Inject constructor(private val placeRemote: PlaceRemote) :
    PlaceDataStore {
    override suspend fun getPlacesAutocomplete(                                               queryString: String): ResultWrapper<List<PlaceEntity>> {

        return placeRemote.getPlacesAutocomplete( queryString)
    }


}