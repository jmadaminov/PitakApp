package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.PlaceEntity


interface PlaceDataStore {
    suspend fun getPlacesAutocomplete(token: String, queryString:String): ResultWrapper<List<PlaceEntity>>

}