package com.badcompany.data.source

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.PlaceEntity
import com.badcompany.data.repository.PlaceDataStore
import com.badcompany.data.repository.PlaceRemote
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