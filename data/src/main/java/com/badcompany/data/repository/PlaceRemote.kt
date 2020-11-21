package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.PlaceEntity

interface PlaceRemote {

   suspend fun getPlacesAutocomplete( queryString:String): ResultWrapper<List<PlaceEntity>>

}