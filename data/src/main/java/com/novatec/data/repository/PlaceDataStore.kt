package com.novatec.data.repository

import com.novatec.core.ResultWrapper
import com.novatec.data.model.PlaceEntity


interface PlaceDataStore {
    suspend fun getPlacesAutocomplete( queryString:String): ResultWrapper<List<PlaceEntity>>

}